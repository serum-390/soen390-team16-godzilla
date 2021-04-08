package ca.serum390.godzilla.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationFailureHandler;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

import ca.serum390.godzilla.service.UserDetailsService;

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf()
                .disable()
                    .authorizeExchange()
                    .pathMatchers(
                        "/api/docs/**",
                        "/api/healthcheck",
                        "/favicon.ico",
                        "/api/signup/**",
                        "/signup/**",
                        "/login/**",
                        "/resources/**",
                        "/static/css/**",
                        "/static/js/**")
                    .permitAll()
                .anyExchange()
                .authenticated().and()
                .formLogin()
                    .loginPage("/login")
                    .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/"))
                    .authenticationFailureHandler(new RedirectServerAuthenticationFailureHandler("/login?error"))
                .and()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    ReactiveAuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
    }
}
