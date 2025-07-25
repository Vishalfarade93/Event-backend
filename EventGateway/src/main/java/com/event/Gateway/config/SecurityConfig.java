package com.event.Gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http.csrf(csrf -> csrf.disable())
				.authorizeExchange(exchanges -> exchanges.pathMatchers("/login/**", "/events/**").permitAll() // Public
																												// routes
						.anyExchange().authenticated() // Everything else needs token
				).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()) // Enable JWT from Okta
				);

		return http.build();
	}
}
