package com.api.gateway;

import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.google.common.net.HttpHeaders;


import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Environment env;

	public AuthorizationHeaderFilter() {
		super(Config.class);
	}

	public static class Config {
		// some configuration properties
	}

	@Override
	public GatewayFilter apply(Config config) {

		return (exchange, chain) -> {

			ServerHttpRequest req = exchange.getRequest();
			if (!req.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "No Authorization Header", HttpStatus.UNAUTHORIZED);
			}
			String authHeader = req.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			logger.info("authHeader" + authHeader);
			String jwt = authHeader.replace("Bearer ","");
			logger.info(jwt);
			if (!isJwtValid(jwt)) {
				return onError(exchange, "JWT is not valid", HttpStatus.UNAUTHORIZED);
			}
			return chain.filter(exchange);
		};
	}

	private Mono<Void> onError(ServerWebExchange exchange, String string, HttpStatus status) {
		ServerHttpResponse res = exchange.getResponse();
		res.setStatusCode(status);
		return res.setComplete();
	}

	private boolean isJwtValid(String jwt) {
		logger.info("entered isJwtValid()");
		boolean isValid = true;
		String subject = null;
		String tokenSecret = env.getProperty("token.secret");
		byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
		SecretKey secretKey = new SecretKeySpec(secretKeyBytes , "HmacSHA256");
		
		JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
								  
		try {
			logger.info("Subject inside try" + subject);
		   subject =  jwtParser.parseSignedClaims(jwt).getPayload().getSubject();
		   logger.info("Subject inside after try" + subject);
		}catch(Exception e) {
			
		}
		if (subject == null || subject.isEmpty()) {
			isValid = false;
		}
		return isValid;
	}

}
