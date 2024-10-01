package com.si2001.webapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.si2001.webapp.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	User findByEmail(String email);
}
