package com.app.user.service;



import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.user.domain.UserEntity;
import com.app.user.domain.UserModel;


public interface UserService extends UserDetailsService  {

	void createUser(UserEntity user);
	UserModel getUserDetailsByEmail(String email);


}
