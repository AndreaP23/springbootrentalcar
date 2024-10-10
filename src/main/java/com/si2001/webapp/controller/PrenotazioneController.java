package com.si2001.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.si2001.webapp.dto.PrenotazioneDTO;
import com.si2001.webapp.response.PrenotazioneResponse;
import com.si2001.webapp.service.PrenotazioneService;

@RestController
@RequestMapping("/api/prenotazioni") 
@CrossOrigin("http://localhost:4200")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @GetMapping("/superuser/listprenotazioni")
    public List<PrenotazioneDTO> showPrenotazioni() {
        return prenotazioneService.getAllPrenotazioni();
    }

    @GetMapping("/listprenotazioni/{userId}")
    public List<PrenotazioneDTO> getPrenotazioniByUserId(@PathVariable Long userId) {
        return prenotazioneService.getPrenotazioniByUserId(userId);
    }
    
    @GetMapping("/listabyprenotazione/{prenotazioneId}")
    public ResponseEntity<PrenotazioneDTO> getPrenotazioneById(@PathVariable Long prenotazioneId) {
        PrenotazioneDTO prenotazioneDTO = prenotazioneService.getPrenotazioneById(prenotazioneId);
        return ResponseEntity.ok(prenotazioneDTO);
    }

    @PostMapping("/salva")
    public ResponseEntity<PrenotazioneResponse> salvaPrenotazione(
            @RequestBody PrenotazioneDTO prenotazioneDTO) { 

        Long userId = prenotazioneDTO.getUserId();
        /* 
        System.out.println("Ricevuto userId: " + userId);
        System.out.println("Ricevuto veicoloId: " + prenotazioneDTO.getVeicoloId());
        System.out.println("Ricevuto dataInizio: " + prenotazioneDTO.getDataInizio());
        System.out.println("Ricevuto dataFine: " + prenotazioneDTO.getDataFine());
        System.out.println("Ricevuto note: " + prenotazioneDTO.getNote());
        */

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

    
    @DeleteMapping("/superuser/deletePrenotazione/{id}")
    public ResponseEntity<Map<String, String>> deletePrenotazione(@PathVariable("id") Long prenotazioneId) {
        Map<String, String> response = new HashMap<>();
        try {
            prenotazioneService.deletePrenotazione(prenotazioneId);
            response.put("message", "Prenotazione eliminata con successo.");
            return ResponseEntity.ok(response);  
        } catch (Exception e) {
            response.put("error", "Errore: Prenotazione non trovata o non eliminata.");
            return ResponseEntity.status(404).body(response);  
        }
    }
    
    @PutMapping("/modifica/{prenotazioneId}")
    public ResponseEntity<PrenotazioneResponse> modificaPrenotazione(
            @PathVariable Long prenotazioneId,
            @RequestBody PrenotazioneDTO prenotazioneDTO) {

        PrenotazioneResponse response = prenotazioneService.modificaPrenotazione(prenotazioneId, prenotazioneDTO);

        if (response.isValidated()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }


}
