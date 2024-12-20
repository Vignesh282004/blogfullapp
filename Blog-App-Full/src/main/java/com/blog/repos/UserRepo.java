package com.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.User;

public interface UserRepo extends JpaRepository<User,Long>{
	User findByEmail(String email);
}
