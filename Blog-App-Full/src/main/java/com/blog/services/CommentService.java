package com.blog.services;

import java.util.List;

import com.blog.dtos.CommentDto;

public interface CommentService {
	 void createComment(String url, CommentDto commentDto);

	    List<CommentDto> findAllComments();

	    void deleteComment(Long commentId);
	    List<CommentDto> findCommentsByPost();
}
