package com.si2001.webapp.service;

import java.util.List;

import com.si2001.webapp.dto.UserDTO;


public interface UserService {

	void saveUser(UserDTO userDto);
	List<UserDTO> getAllUsers();
}
