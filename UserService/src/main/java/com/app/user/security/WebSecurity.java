
package com.app.user.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration

@EnableWebSecurity
public class WebSecurity {

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(authorizeHttpRequest -> {
			authorizeHttpRequest.requestMatchers(new AntPathRequestMatcher("/users/create/user")).permitAll()
					.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll();
		}).sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.headers(headers -> {

			headers.frameOptions(frameOptions -> frameOptions.disable());
		});

		return http.build();

	}
}
