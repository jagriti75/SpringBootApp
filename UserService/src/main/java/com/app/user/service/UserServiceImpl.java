package com.app.user.service;


import java.util.ArrayList;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.user.data.UserRepository;
import com.app.user.domain.UserEntity;
import com.app.user.domain.UserModel;

@Service
public class UserServiceImpl implements UserService {

	
	UserRepository repo;
	BCryptPasswordEncoder bCryptPasswordEncoder;
	

	public UserServiceImpl(UserRepository repo ,BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.repo = repo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	public void createUser(UserEntity user) {
		user.setUserId(UUID.randomUUID().toString());
		user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		repo.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = repo.findByEmail(username);
		if(userEntity == null) throw new UsernameNotFoundException(username);
		
		
		return new User(userEntity.getEmail() , userEntity.getEncryptedPassword() ,
				true ,true , true , true , new ArrayList<>());
	}

	@Override
	public UserModel getUserDetailsByEmail(String email) {
		UserEntity userEntity = repo.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		return new ModelMapper().map(userEntity , UserModel.class);
	}


}
