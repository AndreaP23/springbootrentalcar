package com.si2001.webapp.service;

import java.util.List;

import com.si2001.webapp.dto.UserDTO;
import com.si2001.webapp.entities.User;


public interface UserService {

	void saveUser(UserDTO userDto);
	List<UserDTO> getAllUsers();
	User findByEmail(String email);
	void deleteUser(Long userId);
}
