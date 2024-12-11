package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.blog.dtos.RegisterDto;
import com.blog.entity.User;
import com.blog.services.impl.UserServiceImpl;

import jakarta.validation.Valid;

@Controller
public class AuthController {

	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@GetMapping("/register")
	public String loginform(Model model) {
		RegisterDto registerDto = new RegisterDto();
		model.addAttribute("user",registerDto);
		return "register";
	}
	
	
	@PostMapping("/register/save")
	public String register(@Valid @ModelAttribute("user") RegisterDto user, BindingResult result , Model model) 
	{
		User existingUser = userServiceImpl.findByEmail(user.getEmail());
		
		if(existingUser != null) {
			result.rejectValue("email", null, "user already exists");
		}
		if(result.hasErrors()) {
			model.addAttribute("user",user);
			return "register";
		}
		
		userServiceImpl.saveUser(user);
		return "redirect:/register?success";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
