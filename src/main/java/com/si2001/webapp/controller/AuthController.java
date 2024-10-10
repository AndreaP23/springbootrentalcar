package com.si2001.webapp.controller;

import com.si2001.webapp.dto.UserDTO;
import com.si2001.webapp.entities.User;
import com.si2001.webapp.mapper.UserMapper;
import com.si2001.webapp.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> saveUser(@RequestBody UserDTO userDto) {
        userService.saveUser(userDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Utente registrato con successo!");
        return ResponseEntity.ok(response);  
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDto) {
        User user = userService.findByEmail(userDto.getEmail());

        if (user != null && user.getPassword().equals(userDto.getPassword())) {
            UserDTO loggedInUser = userMapper.toDTO(user);
            loggedInUser.setPassword(null); 

            return ResponseEntity.ok(Map.of("message", "Login effettuato con successo!", "user", loggedInUser)); 
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Email o password errati."));
        }
    }

    @GetMapping("/superuser/listuser")
    public ResponseEntity<List<UserDTO>> showUser() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @DeleteMapping("/superuser/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.deleteUser(id);
            response.put("message", "Utente eliminato con successo.");
            return ResponseEntity.ok(response);  
        } catch (Exception e) {
            response.put("error", "Errore: utente non trovato o non eliminato.");
            return ResponseEntity.status(404).body(response);  
        }
    }


}
