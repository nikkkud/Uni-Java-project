package com.online.poker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails userDetails1 = User.withDefaultPasswordEncoder()
			.username("Ivan")
			.password("p")
			.roles("USER")
			.build();
		UserDetails userDetails2 = User.withDefaultPasswordEncoder()
			.username("Maria")
			.password("password_2")
			.roles("USER")
			.build();
		UserDetails userDetails3 = User.withDefaultPasswordEncoder()
			.username("Gleb")
			.password("password_3")
			.roles("USER")
			.build();
		UserDetails userDetails4 = User.withDefaultPasswordEncoder()
			.username("Nikita")
			.password("password_4")
			.roles("USER")
			.build();

		return new InMemoryUserDetailsManager(userDetails1,userDetails2,userDetails3,userDetails4);
	}

}