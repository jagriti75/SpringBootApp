package com.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import reactor.core.publisher.Mono;

@Configuration
public class GlobalFiltersConfiguration {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//order of prefiter will have the most priority and post filter will have the least
	@Order(1)
	@Bean
	public GlobalFilter secondPreFilter() {
		return (exchange , chain)->{
			logger.info("My second Global Pre filter is executed.....");
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				logger.info("My second global post filter is executed .....");
			}));
		};
	}
	@Order(2)
	@Bean
	public GlobalFilter thirdPreFilter() {
		return (exchange , chain)->{
			logger.info("My Third Global Pre filter is executed.....");
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				logger.info("My Third global post filter is executed .....");
			}));
		};
	}
	
	@Order(3)
	@Bean
	public GlobalFilter fourthPreFilter() {
		return (exchange , chain)->{
			logger.info("My fourth Global Pre filter is executed.....");
			return chain.filter(exchange).then(Mono.fromRunnable(()->{
				logger.info("My fourth global post filter is executed .....");
			}));
		};
	}
}
