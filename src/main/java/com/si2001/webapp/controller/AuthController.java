package com.si2001.webapp.controller;

import com.si2001.webapp.entities.User;
import com.si2001.webapp.mapper.UserMapper;
import com.si2001.webapp.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.openapitools.api.AuthApi;
import org.openapitools.model.LoginUserRequest;
import org.openapitools.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthController implements AuthApi {

    @Autowired
    private UserService userService;


    @Override
    public ResponseEntity<Void> registerUser(UserDTO userDTO) {
        userService.saveUser(userDTO);
        return ResponseEntity.ok().build();
    }



    @Override
    public ResponseEntity<Void> loginUser(@Valid @RequestBody LoginUserRequest loginUserRequest) {
        UserDTO user = userService.findByEmail(loginUserRequest.getEmail());

        if (user != null && user.getPassword().equals(loginUserRequest.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).build(); // Email o password errati
        }
    }



    @Override
    public ResponseEntity<UserDTO> getUserByEmail(
            @NotNull @Parameter(name = "email", description = "Email dell'utente da cercare", required = true) @Valid @RequestParam(value = "email") String email) {

        UserDTO userDto = userService.findByEmail(email); // Ottieni direttamente UserDTO

        if (userDto != null) {
            return ResponseEntity.ok(userDto); // Ritorna UserDTO
        } else {
            return ResponseEntity.status(404).body(null); // Ritorna 404 se non trovato
        }
    }



    @Override
    public ResponseEntity<UserDTO> getUserById(Integer userId) {
        UserDTO userDto = userService.findById(userId.longValue());
        if (userDto != null) {
            return ResponseEntity.ok(userDto); // Restituisce UserDTO
        } else {
            return ResponseEntity.status(404).build(); // Utente non trovato
        }
    }
}
