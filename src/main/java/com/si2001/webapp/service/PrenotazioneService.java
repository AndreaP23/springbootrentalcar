package com.si2001.webapp.service;

import java.util.List;

import com.si2001.webapp.dto.PrenotazioneDTO;
import com.si2001.webapp.response.PrenotazioneResponse;

public interface PrenotazioneService {

	    List<PrenotazioneDTO> getAllPrenotazioni();
	    PrenotazioneResponse salvaPrenotazione(Long userId, PrenotazioneDTO prenotazioneDTO);
	    void deletePrenotazione(Long prenotazioneId);
	    List<PrenotazioneDTO> getPrenotazioniByUserId(Long userId);
	    PrenotazioneResponse modificaPrenotazione(Long prenotazioneId, PrenotazioneDTO prenotazioneDTO);
	    PrenotazioneDTO getPrenotazioneById(Long prenotazioneId); 

	
	
}
