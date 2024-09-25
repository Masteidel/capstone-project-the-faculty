package learn.register.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors() // Enable CORS
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Use stateless sessions (JWT-based)
                .and()
                .authorizeRequests()

                // Allow public access to registration and authentication endpoints
                .antMatchers("/api/user/register", "/api/user/authenticate").permitAll()

                // Allow both students and professors to perform GET requests
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyRole("STUDENT", "PROFESSOR")
                .antMatchers(HttpMethod.POST, "/api/enrollments").hasAnyRole("STUDENT")
                .antMatchers(HttpMethod.POST, "/api/students").hasAnyRole("STUDENT")
                // Only professors can POST, PUT, and DELETE resources
                .antMatchers(HttpMethod.POST, "/api/**").hasRole("PROFESSOR")
                .antMatchers(HttpMethod.PUT, "/api/**").hasRole("PROFESSOR")
                .antMatchers(HttpMethod.DELETE, "/api/**").hasRole("PROFESSOR")

                // Require authentication for all other requests
                .anyRequest().authenticated()

                // Add JWT authentication filter
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(converter), UsernamePasswordAuthenticationFilter.class);  // Custom filter for JWT authentication // Or formLogin() if you use form-based authentication

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use BCrypt for password hashing
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("*");
            }
        };
    }
}
