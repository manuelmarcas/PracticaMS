package com.practica.gateway;


import com.practica.gateway.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class Security {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return (username) -> userRepository.findByUsername(username);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec = http.authorizeExchange();

        authorizeExchangeSpec.pathMatchers("/crear/**").permitAll();
        authorizeExchangeSpec.pathMatchers("/actuator").hasRole("USER");
        authorizeExchangeSpec.pathMatchers("/**").authenticated();
        authorizeExchangeSpec.and().csrf().disable().httpBasic();
        return http.build();

    }

}