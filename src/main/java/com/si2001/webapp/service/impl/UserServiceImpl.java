package com.si2001.webapp.service.impl;

import com.si2001.webapp.dao.UserRepository;
import com.si2001.webapp.entities.User;
import com.si2001.webapp.mapper.UserMapper;
import com.si2001.webapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	//Iniezione tramite costruttore
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Override
	public void saveUser(UserDTO userDto) {
		User user = userMapper.toEntity(userDto);
		// Se il ruolo non è specificato, assegna il ruolo 'Customer' (ruolo ID 2)
		if (user.getRuolo() == null) {
			user.setRuolo(2);
		}
		userRepository.save(user);
	}

	@Override
	public Page<UserDTO> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable)
				.map(user -> userMapper.toDTO(user));
	}

	@Override
	public UserDTO findByEmail(String email) {
		return userRepository.findByEmail(email)
				.map(userMapper::toDTO) // Usa il mapper per convertire User in UserDTO
				.orElse(null); // Restituisce null se non trovato
	}

	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(Long.valueOf(userId));
	}

	@Override
	public UserDTO findById(Long userId) {
		return userRepository.findById(userId)
				.map(userMapper::toDTO)
				.orElse(null);
	}

	@Override
	public Page<UserDTO> getUsersWithPaginationAndFilter(Specification<User> specification, Pageable pageable) {
		Page<User> usersPage = userRepository.findAll(specification, pageable);
		return usersPage.map(userMapper::toDTO);
	}
}
