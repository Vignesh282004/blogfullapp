package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.blog.dtos.CommentDto;
import com.blog.dtos.PostDto;
import com.blog.repos.UserRepo;
import com.blog.services.impl.CommentServiceImpl;
import com.blog.services.impl.PostServiceImpl;
import com.blog.utils.SecurityUtils;

import jakarta.validation.Valid;

@Controller
public class CommentControllers {

	@Autowired
	private CommentServiceImpl commentServiceImpl;
	
	@Autowired
	private PostServiceImpl postServiceImpl;
	
	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/{postUrl}/comments")
	public String postCoomment(@PathVariable("postUrl") String postUrl, Model model,@Valid @ModelAttribute("comment") CommentDto commentDto, BindingResult result) 
	{
		PostDto postDto = postServiceImpl.findPostsbyUrl(postUrl);
		if(result.hasErrors()) {
			model.addAttribute("post",postDto);
			model.addAttribute("comment",commentDto);
			return "blog/blog_post";
		}
			String email = SecurityUtils.getCurrentUser().getUsername();
			Long userId = userRepo.findByEmail(email).getId();
			commentServiceImpl.createComment(postUrl, commentDto);
			return "redirect:/post/" + postUrl;
		}
	}