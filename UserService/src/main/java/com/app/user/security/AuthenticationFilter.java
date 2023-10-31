package com.app.user.security;

import java.io.IOException;
import java.security.Key;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.user.domain.LoginModel;
import com.app.user.domain.UserModel;
import com.app.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private UserService userService;
	private Environment env;
	
	
	public AuthenticationFilter(UserService userService , Environment env , AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.userService = userService;
		this.env = env;
		
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req , HttpServletResponse res)
	throws AuthenticationException{
		
		try {
			LoginModel creds = new ObjectMapper().readValue(req.getInputStream() , LoginModel.class);
			UsernamePasswordAuthenticationToken authRequest = 
					UsernamePasswordAuthenticationToken.unauthenticated(creds.getEmail(),creds.getPassword());
			setDetails(req , authRequest);
			return this.getAuthenticationManager().authenticate(authRequest);
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest req , HttpServletResponse res ,
			FilterChain chain , Authentication auth)throws IOException , ServletException{
		
		    String username = ((User)auth.getPrincipal()).getUsername();
		    UserModel userDetails = userService.getUserDetailsByEmail(username);
		    String tokenSecret = env.getProperty("token.secret");
		    
		    byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
		    
		    Key secretKey = Keys.hmacShaKeyFor(secretKeyBytes);
		    
		    String token = Jwts.builder().claims().subject(userDetails.getUserId())
		    						.expiration(Date.from(Instant.now().plusMillis(3600000)))
		    						.issuedAt(Date.from(Instant.now()))
		    						.and()
		    						.signWith(secretKey)
		    						.compact();
		    
		    res.addHeader("token", token);
		    res.addHeader("userId", userDetails.getUserId());
		    
		    
		    
	}

}
