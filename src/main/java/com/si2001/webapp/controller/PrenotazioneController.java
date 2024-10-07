package com.si2001.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.si2001.webapp.dto.PrenotazioneDTO;
import com.si2001.webapp.response.PrenotazioneResponse;
import com.si2001.webapp.service.PrenotazioneService;

@RestController
@RequestMapping("/api/prenotazioni") 
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping("/superuser/listprenotazioni")
    public List<PrenotazioneDTO> showPrenotazioni() {
        return prenotazioneService.getAllPrenotazioni();
    }

    @PostMapping("/salva")
    public ResponseEntity<PrenotazioneResponse> salvaPrenotazione(
           @RequestBody PrenotazioneDTO prenotazioneDTO) { 

        Long userId = prenotazioneDTO.getUserId(); 

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); 
        }

        PrenotazioneResponse response = prenotazioneService.salvaPrenotazione(userId, prenotazioneDTO);

        if (response.isValidated()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response); 
        } else {
            return ResponseEntity.badRequest().body(response); 
        }
    }
}
