package com.blog.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.blog.dtos.PostDto;
import com.blog.entity.Post;

public interface PostService {
		Page<PostDto> findallPosts(int page, int size);
		void createPost(PostDto postDto);
		void updatePost(PostDto postDto);
		PostDto findpostsbyId(Long userId);
		void deletePost(Long userId);
		PostDto findPostsbyUrl(String url);
		Page<PostDto> searchPost(String query ,int page, int size);
		List<PostDto> findPostsByUser();
		List<PostDto> getadminPosts();
}
