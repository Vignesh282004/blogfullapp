package com.blog.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entity.Role;

public interface RoleRepo extends JpaRepository<Role,Long> {
   Role findByName(String name);
}
