package com.blog.services.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.dtos.RegisterDto;
import com.blog.entity.Role;
import com.blog.entity.User;
import com.blog.repos.RoleRepo;
import com.blog.repos.UserRepo;
import com.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;
		
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void saveUser(RegisterDto registerDto) {
		User user = new User();
		user.setEmail(registerDto.getEmail());
		user.setName(registerDto.getFirstName() + " " + registerDto.getLastName());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		Role role = roleRepo.findByName("ROLE_USER");
		user.setRoles(Arrays.asList(role));
		userRepo.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
}
