package learn.register.controllers;

import learn.register.models.AppUser;
import learn.register.security.AppUserService;
import learn.register.security.JwtConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtConverter converter;
    private final AppUserService appUserService;

    @Value("${professor.registration.code}")
    private String professorCode;

    public AuthController(AuthenticationManager authenticationManager, JwtConverter converter, AppUserService appUserService) {
        this.authenticationManager = authenticationManager;
        this.converter = converter;
        this.appUserService = appUserService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody Map<String, String> credentials) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"));

        try {
            Authentication authentication = authenticationManager.authenticate(authToken);

            if (authentication.isAuthenticated()) {
                // Cast to AppUser to access roles
                AppUser appUser = (AppUser) authentication.getPrincipal();

                // Log the roles in AppUser
                System.out.println("User's roles after authentication: " + appUser.getAuthorities());

                // Generate JWT with AppUser's authorities
                String jwtToken = converter.getTokenFromUser(appUser);

                HashMap<String, String> map = new HashMap<>();
                map.put("jwt_token", jwtToken);

                return new ResponseEntity<>(map, HttpStatus.OK);
            }

        } catch (AuthenticationException ex) {
            System.out.println(ex);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createAccount(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");
            String extraCode = credentials.get("code"); // The code provided by the user

            // Default role is STUDENT
            String role = "ROLE_STUDENT";

            // Check if the code matches the professor code
            if (professorCode.equals(extraCode)) {
                role = "ROLE_PROFESSOR";
            }

            // Create the user with the specified role
            AppUser appUser = appUserService.create(username, password, role);

            // Automatically authenticate the user after registration
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(authToken);

            // If authentication is successful, generate a JWT token
            if (authentication.isAuthenticated()) {
                String jwtToken = converter.getTokenFromUser((User) authentication.getPrincipal());

                HashMap<String, String> map = new HashMap<>();
                map.put("jwt_token", jwtToken);

                return new ResponseEntity<>(map, HttpStatus.CREATED);
            }

        } catch (ValidationException ex) {
            return new ResponseEntity<>(List.of(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (DuplicateKeyException ex) {
            return new ResponseEntity<>(List.of("The provided username already exists"), HttpStatus.BAD_REQUEST);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>("Registration succeeded, but login failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // If something goes wrong with authentication, return an error
        return new ResponseEntity<>("User registered but login failed", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/register/professor")
    @PreAuthorize("hasRole('ROLE_PROFESSOR')")  // Ensure only professors can create professor accounts
    public ResponseEntity<?> createProfessorAccount(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            // Directly assign the PROFESSOR role
            AppUser appUser = appUserService.create(username, password, "PROFESSOR");

            // Return the appUserId after successful user creation
            HashMap<String, Integer> map = new HashMap<>();
            map.put("appUserId", appUser.getAppUserId());

            return new ResponseEntity<>(map, HttpStatus.CREATED);
        } catch (ValidationException ex) {
            return new ResponseEntity<>(List.of(ex.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (DuplicateKeyException ex) {
            return new ResponseEntity<>(List.of("The provided username already exists"), HttpStatus.BAD_REQUEST);
        }
    }
}

