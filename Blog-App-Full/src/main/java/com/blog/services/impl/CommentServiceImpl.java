package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dtos.CommentDto;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.entity.User;
import com.blog.mappers.CommentMapper;
import com.blog.repos.CommentRepo;
import com.blog.repos.PostRepo;
import com.blog.repos.UserRepo;
import com.blog.services.CommentService;
import com.blog.utils.SecurityUtils;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public void createComment(String url, CommentDto commentDto) {
		Post post = postRepo.findByUrl(url).get();
		Comment comment = CommentMapper.mapToEntity(commentDto);
		comment.setPost(post);
		commentRepo.save(comment);
	}

	@Override
	public List<CommentDto> findAllComments() {
		List<Comment> comments = commentRepo.findAll();
		 return comments.stream().map(CommentMapper::mapToDto).collect(Collectors.toList());
	}

	@Override
	public void deleteComment(Long commentId) {
		commentRepo.deleteById(commentId);
	}

	@Override
	public List<CommentDto> findCommentsByPost() {
		String email = SecurityUtils.getCurrentUser().getUsername();
		User createdBy = userRepo.findByEmail(email);
		Long userId = createdBy.getId();
		List<Comment> commentDtos = commentRepo.findCommentByPost(userId);
		return commentDtos.stream().map((comment) -> CommentMapper.mapToDto(comment)).collect(Collectors.toList());
	}

}
