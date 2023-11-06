package com.app.user.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.app.user.service.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	private Environment environment;
	private UserService userService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity(UserService userService ,Environment environment, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.environment = environment;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
		
		//Configure AuthenticationManagerBuilder
		AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authBuilder.userDetailsService(userService)
					.passwordEncoder(bCryptPasswordEncoder);
		
		AuthenticationManager authenticationManager = authBuilder.build();
		
		//Create AuthenticationFilter
		AuthenticationFilter authenticationFilter =  new AuthenticationFilter(userService , environment , authenticationManager);
		//Customized loginURL path 
		authenticationFilter.setFilterProcessesUrl("/login");
		http.csrf(csrf -> csrf.disable());
		
		http.authorizeHttpRequests(auth -> auth.requestMatchers(new AntPathRequestMatcher("/users")).permitAll()
			.requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
			.requestMatchers(new AntPathRequestMatcher("/users/greet")).authenticated()
		)
		.addFilter(authenticationFilter)
		.authenticationManager(authenticationManager)
		.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.headers(headers ->headers.frameOptions(frameOptions -> frameOptions.disable()));
		return http.build();
	}

}
