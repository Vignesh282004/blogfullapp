package com.blog.services;

import com.blog.dtos.RegisterDto;
import com.blog.entity.User;

public interface UserService {
	void saveUser(RegisterDto registerDto);
	
	User findByEmail(String email);
}
