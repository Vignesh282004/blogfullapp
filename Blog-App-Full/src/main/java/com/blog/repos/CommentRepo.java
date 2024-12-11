package com.blog.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blog.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment,Long> {
	
	
	@Query(value = "select c.* from comments c inner join posts p where c.post_id = p.id and p.created_by = :userId",nativeQuery = true)
	List<Comment> findCommentByPost(Long userId);
}
