package com.si2001.webapp.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.si2001.webapp.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	Optional<User> findByEmail(String email);
}
