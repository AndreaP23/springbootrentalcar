package com.si2001.webapp.service;

import java.util.List;
import org.openapitools.model.UserDTO;
import com.si2001.webapp.entities.User;
import org.openapitools.model.VeicoloDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
	void saveUser(UserDTO userDto); // Salva un nuovo utente
	//List<UserDTO> getAllUsers(); // Ottiene tutti gli utenti come UserDTO
	UserDTO findByEmail(String email); // Restituisce UserDTO per l'email fornita
	void deleteUser(Long userId); // Elimina un utente per ID
	UserDTO findById(Long userId); // Restituisce UserDTO per ID
	Page<UserDTO> getUsersWithPaginationAndFilter(Specification<User> specification, Pageable pageable); // Ottiene paginazione e filtri
	Page<UserDTO> getAllUsers(Pageable pageable);
}
