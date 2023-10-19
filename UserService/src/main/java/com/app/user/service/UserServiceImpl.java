package com.app.user.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.user.data.UserRepository;
import com.app.user.domain.User;
@Service
public class UserServiceImpl implements UserService {

	
	UserRepository repo;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired	
	public UserServiceImpl(UserRepository repo ,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.repo = repo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public void createUser(User user) {
		user.setUserId(UUID.randomUUID().toString());
		user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		repo.save(user);
	}
}
