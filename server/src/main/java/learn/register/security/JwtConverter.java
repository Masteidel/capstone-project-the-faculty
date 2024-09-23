package learn.register.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtConverter {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final String ISSUER = "register";
    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;
}
