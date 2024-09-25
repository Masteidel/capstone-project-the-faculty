package learn.register.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtConverter {

    // 1. Signing key
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 2. "Configurable" constants
    private final String ISSUER = "register";  // Changed to "register"
    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

    public String getTokenFromUser(User user) {
        // Log the authorities to check if they are being retrieved correctly
        System.out.println("Authorities received in getTokenFromUser: " + user.getAuthorities());

        String authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Log the authorities string before setting it in the token
        System.out.println("Authorities to be set in the token: " + authorities);

        return Jwts.builder()
                .setIssuer("register")
                .setSubject(user.getUsername())
                .claim("authorities", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }

    public User getUserFromToken(String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            // 4. Use JJWT classes to read a token.
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)  // Ensure the token has the correct issuer
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.substring(7));  // Remove "Bearer " prefix

            String username = jws.getBody().getSubject();
            String authStr = (String) jws.getBody().get("authorities");
            List<GrantedAuthority> authorities = Arrays.stream(authStr.split(","))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new User(username, username, authorities);

        } catch (JwtException e) {
            // 5. JWT failures are modeled as exceptions.
            System.out.println(e);
        }

        return null;
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Authentication getAuthenticationFromToken(String token) {
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        String username = jws.getBody().getSubject();
        String authorities = (String) jws.getBody().get("authorities");
        List<GrantedAuthority> grantedAuthorities = Arrays.stream(authorities.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
    }
}
