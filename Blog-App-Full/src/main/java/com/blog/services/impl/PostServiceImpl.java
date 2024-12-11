package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.ErrorPageRegistrarBeanPostProcessor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blog.dtos.PostDto;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.mappers.PostMapper;
import com.blog.repos.PostRepo;
import com.blog.repos.UserRepo;
import com.blog.services.PostService;
import com.blog.utils.SecurityUtils;

@Service
public class PostServiceImpl  implements PostService{
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	
	
	@Override
	public Page<PostDto> findallPosts(int page, int size) {
		
			//for pagination
		
		Pageable pageable = PageRequest.of(page, size);
		Page<Post> page2 = postRepo.findAll(pageable);
		return page2.map(PostMapper::mapToPostDto);
		
				//for no pagination simply do below code
		
		//	 List<Post> posts = postRepo.findAll();
			//return posts.stream().map((post) -> PostMapper.mapToPostDto(post)).collect(Collectors.toList());
	}

	@Override
	public void createPost(PostDto postDto) {
		String email = SecurityUtils.getCurrentUser().getUsername();
		User user = userRepo.findByEmail(email);
		
		Post post = PostMapper.mapToPost(postDto);
		post.setCreatedBy(user);
		postRepo.save(post);
		
	}

	@Override
	public void updatePost(PostDto postDto) {
		String email = SecurityUtils.getCurrentUser().getUsername();
		User user = userRepo.findByEmail(email);
		Post post = PostMapper.mapToPost(postDto);
		post.setCreatedBy(user);
		postRepo.save(post);
	}

	@Override
	public PostDto findpostsbyId(Long userId) {
		Post post = postRepo.findById(userId).get();
		return PostMapper.mapToPostDto(post);
	}

	@Override
	public void deletePost(Long userId) {
		postRepo.deleteById(userId);
	}

	@Override
	public PostDto findPostsbyUrl(String url) {
		Post post = postRepo.findByUrl(url).get();
		return PostMapper.mapToPostDto(post);
	}

	@Override
	public Page<PostDto> searchPost(String query,int page, int size) {
		Pageable pageable = PageRequest.of(page,size);
		
		Page<Post> page2 = postRepo.searchPosts(query,pageable);
		return page2.map(PostMapper::mapToPostDto);
		//List<Post> posts = postRepo.searchPosts(query);
		//return posts.stream().map((post) -> PostMapper.mapToPostDto(post)).collect(Collectors.toList());
	}

	@Override
	public List<PostDto> findPostsByUser() {
		String email = SecurityUtils.getCurrentUser().getUsername();
		User user = userRepo.findByEmail(email);
		Long userId = user.getId();
		List<Post> posts = postRepo.findPostsByUser(userId);
		return posts.stream().map((post) -> PostMapper.mapToPostDto(post)).collect(Collectors.toList());
	}

	@Override
	public List<PostDto> getadminPosts() {
		List<Post> posts = postRepo.findAll();
		return posts.stream().map((post) -> PostMapper.mapToPostDto(post)).collect(Collectors.toList());
	}

}
