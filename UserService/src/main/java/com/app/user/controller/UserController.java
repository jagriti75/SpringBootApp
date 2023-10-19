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

import com.app.user.domain.User;
import com.app.user.service.UserService;

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
	
	@PostMapping("/create/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		service.createUser(user);
		return new ResponseEntity<User>(user , HttpStatus.CREATED);
	}
}
