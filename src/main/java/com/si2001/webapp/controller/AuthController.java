package com.si2001.webapp.controller;

import com.si2001.webapp.dto.UserDTO;
import com.si2001.webapp.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")  
    public ResponseEntity<String> saveUser(@RequestBody UserDTO userDto) {
        userService.saveUser(userDto); 
        return ResponseEntity.ok("Utente registrato con successo!"); 
}
    
    @GetMapping("/listuser")
    public List<UserDTO> showUser(){
    	return userService.getAllUsers();
    }
    
}
