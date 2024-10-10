package com.ticket.java.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests()
				.requestMatchers("/tickets", "/tickets/show/**", "/tickets/note/**", "/users/**", "fragments/**")
				.hasAnyAuthority("ADMIN", "USER").requestMatchers("tickets/statusupdate/**").hasAuthority("USER")
				.requestMatchers("/tickets/edit/**", "/tickets/create", "/tickets/delete/**").hasAnyAuthority("ADMIN")
				.requestMatchers("/api/**", "/webjars/**", "/css/**","/error").permitAll().and().formLogin().loginPage("/")
				.loginProcessingUrl("/").defaultSuccessUrl("/tickets", true).permitAll().and().logout().and()
				.exceptionHandling();

		return http.build();
	}

	@Bean
	DbTicketUserDetailsService userDetailsService() {
		return new DbTicketUserDetailsService();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;

	}
}
