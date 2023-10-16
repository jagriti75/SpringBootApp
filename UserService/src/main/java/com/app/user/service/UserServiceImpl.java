package com.app.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.user.data.UserRepository;
import com.app.user.domain.User;
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository repo;
	
	public void createUser(User user) {
		
		repo.save(user);
	}
}
