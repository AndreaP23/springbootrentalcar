package com.si2001.webapp.controller;

import com.si2001.webapp.service.PrenotazioneService;
import org.openapitools.api.PrenotazioniApi;
import org.openapitools.model.PrenotazioneDTO;
import org.openapitools.model.SearchPrenotazioni200Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PrenotazioneController implements PrenotazioniApi {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Override
    public ResponseEntity<List<PrenotazioneDTO>> showPrenotazioni() {
        List<PrenotazioneDTO> prenotazioni = prenotazioneService.getAllPrenotazioni();
        return ResponseEntity.ok(prenotazioni);
    }

    @Override
    public ResponseEntity<SearchPrenotazioni200Response> searchPrenotazioni(Integer page, Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 10;

        Pageable pageable = PageRequest.of(page, size);

        Page<PrenotazioneDTO> userPage = prenotazioneService.getAllPrenotazioni(pageable);
        SearchPrenotazioni200Response response = new SearchPrenotazioni200Response();
        response.setContent(userPage.getContent());
        response.setTotalElements(userPage.getNumberOfElements());
        response.setTotalPages(userPage.getTotalPages());
        response.setSize(userPage.getSize());
        response.setNumber(response.getTotalElements());
        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<List<PrenotazioneDTO>> getPrenotazioniByUserId(Integer userId) {
        List<PrenotazioneDTO> prenotazioni = prenotazioneService.getPrenotazioniByUserId(userId.longValue());

        if (prenotazioni.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(prenotazioni);
    }

    @Override
    public ResponseEntity<PrenotazioneDTO> getPrenotazioneById(Integer prenotazioneId) {
        PrenotazioneDTO prenotazione = prenotazioneService.getPrenotazioneById(prenotazioneId.longValue());
        if (prenotazione == null) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(prenotazione);
    }

    //Manca un controllo sull'errore specifico
    @Override
    public ResponseEntity<Void> salvaPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        // Controlla se l'userId è presente
        if (prenotazioneDTO.getUserId() == null) {
            return ResponseEntity.status(400).build();
        }

        if(prenotazioneDTO.getDataPrenotazione() == null || prenotazioneDTO.getDataInizio() == null || prenotazioneDTO.getDataFine() == null) {
            return ResponseEntity.status(400).build();
        }


        // Converte userId da Integer a Long
        Long userId = prenotazioneDTO.getUserId().longValue();

        prenotazioneService.salvaPrenotazione(userId, prenotazioneDTO);
        return ResponseEntity.status(201).build();
    }


    @Override
    public ResponseEntity<Void> modificaPrenotazione(Integer prenotazioneId, PrenotazioneDTO prenotazioneDTO) {
        try {
            prenotazioneService.modificaPrenotazione(prenotazioneId.longValue(), prenotazioneDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @Override
    public ResponseEntity<Void> deletePrenotazione(Integer id) {
        if (!prenotazioneService.existsById(id.longValue())) {
            return ResponseEntity.status(404).build();
        }
        prenotazioneService.deletePrenotazione(id.longValue());
        return ResponseEntity.ok().build();
    }
}
