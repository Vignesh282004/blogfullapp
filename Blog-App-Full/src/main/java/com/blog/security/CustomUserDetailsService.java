package com.blog.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.entity.User;
import com.blog.repos.UserRepo;

@Service
public class CustomUserDetailsService  implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if(user != null) 
		{
			org.springframework.security.core.userdetails.User myUser = new org.springframework.security.core.userdetails.User(
						user.getEmail(),user.getPassword(),
						user.getRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
			return myUser;
		}
		else {
			throw new UsernameNotFoundException("Invalid username or password !");
		}
	}

}
