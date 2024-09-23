package learn.register.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtRequestFilter extends BasicAuthenticationFilter {

    public JwtRequestFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
}
