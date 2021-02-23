package ca.serum390.godzilla.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SeurityConfig {
    @Bean
    public SecurityWebFilterChain SecurityWebFilterChain(ServerHttpSecurity http){
        return http
                .csrf().disable()
                .authorizeExchange()
                .anyExchange().authenticated()
                .and()
                .formLogin().and()
                .httpBasic().and().build();
    }

    @Bean
    ReactiveAuthenticationManager authenticationManager(erp_UserService erp_UserService1){
        return new UserDetailsReactiveAuthenticationManager(erp_UserService1);

    }

}
