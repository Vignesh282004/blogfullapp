package com.blog.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blog.entity.Post;

public interface PostRepo extends JpaRepository<Post,Long>{
	
	Optional<Post> findByUrl(String url);
	
	
	@Query(value = "select *from posts p where p.created_by = :userId" , nativeQuery = true)
	List<Post> findPostsByUser(Long userId);
	
	
	
	@Query(value = "select p from Post p where LOWER(p.title) like CONCAT('%' , LOWER(:query) , '%') OR  LOWER(p.shortDescription) LIKE CONCAT('%', LOWER(:query), '%')")
	Page<Post> searchPosts(String query,Pageable pageable);
	
	//Page<Post> searchPosts(Pageable pageable);
	
}
