package com.si2001.webapp.controller;

import com.si2001.webapp.dto.UserDTO;
import com.si2001.webapp.entities.User;
import com.si2001.webapp.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:4200")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userDto) {
        userService.saveUser(userDto);
        return ResponseEntity.ok("Utente registrato con successo!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDto) {
        User user = userService.findByEmail(userDto.getEmail());

        if (user != null && user.getPassword().equals(userDto.getPassword())) {
            return ResponseEntity.ok("Login effettuato con successo!"); 
        } else {
            return ResponseEntity.status(401).body("Email o password errati.");
        }
    }


    @GetMapping("/superuser/listuser")
    public List<UserDTO> showUser() {
        return userService.getAllUsers();
    }
}
