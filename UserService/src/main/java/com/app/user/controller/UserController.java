package com.app.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.user.domain.UserEntity;
import com.app.user.service.UserService;

import jakarta.ws.rs.core.MediaType;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	Environment env;
	
	@Autowired
	UserService service;

	@GetMapping("/greet")
	public String sayHello() {
		return "hello everybody on "+env.getProperty("local.server.port");
	}
	
	@PostMapping(consumes = {MediaType.APPLICATION_XML ,MediaType.APPLICATION_JSON}
	,produces = {MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML})
	public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
		service.createUser(user);
		return new ResponseEntity<UserEntity>(user , HttpStatus.CREATED);
	}
	
	
}
