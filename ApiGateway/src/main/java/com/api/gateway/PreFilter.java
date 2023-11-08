package com.api.gateway;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class PreFilter implements GlobalFilter , Ordered{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		logger.info("My first Prefilter is executed .....");
		
		String requestPath = exchange.getRequest().getPath().toString();
		logger.info("Request Path: "+requestPath);
		HttpHeaders headers=
		exchange.getRequest().getHeaders();
		
		Set<String> headerNames = headers.keySet();
		headerNames.forEach((headerName)->{
			String headerValue =  headers.getFirst(headerName);
			logger.info(headerName +" "+headerValue);
		});
		
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		//to give it the most priority we either return 0 or -1
		return 0;
	}

}
