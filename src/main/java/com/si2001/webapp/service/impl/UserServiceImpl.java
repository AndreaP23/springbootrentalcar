package com.si2001.webapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.si2001.webapp.dao.UserRepository;
import com.si2001.webapp.dto.UserDTO;
import com.si2001.webapp.entities.User;
import com.si2001.webapp.service.UserService;
import com.si2001.webapp.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository; 
    private final UserMapper userMapper;  

    @Override
    public void saveUser(UserDTO userDto) {
        User user = userMapper.toEntity(userDto);

        if (user.getRuolo() == null) {
            user.setRuolo(2); 
        }


        user.setPassword(userDto.getPassword()); 

        userRepository.save(user);
    }

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream()
				.map(userMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public User findByEmail(String email) {
	    return userRepository.findByEmail(email).orElse(null);
	}
}