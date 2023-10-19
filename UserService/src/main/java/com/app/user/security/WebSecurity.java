
package com.app.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration

@EnableWebSecurity
public class WebSecurity {

	/*
	 * private Environment env;
	 * 
	 * @Autowired public WebSecurity(Environment env) { this.env = env; }
	 */

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable());
		http.authorizeHttpRequests(authorizeHttpRequest -> {
			authorizeHttpRequest.requestMatchers(new AntPathRequestMatcher("/users/create/user"))
			.permitAll()
			//or access(new WebExpressionAuthorizationManager("hasIpAddress('192.08.1.1')"))
			//to allow only specific ip addresses to pass requests
					.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll();
		}).sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.headers(headers -> {

			headers.frameOptions(frameOptions -> frameOptions.disable());
		});

		return http.build();

	}
}
