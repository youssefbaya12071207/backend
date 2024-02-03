package net.javaguide.springboot.security.config;

import lombok.AllArgsConstructor;
import net.javaguide.springboot.security.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    private final UserService userService;
    private final AuthorizationFilter authorizationFilter;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(
                                        "/api/v1/auth/login",
                                        "/api/v1/auth/check-authentication",
                                        "/api/v1/auth/reset-password/**",
                                        "/swagger-ui/*",
                                        "/swagger-ui.html",
                                        "/v3/api-docs/*",
                                        "/ws/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()


                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(httpConfig ->
                httpConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.headers(headers->headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin).disable());
        return http.build();
    }
}
