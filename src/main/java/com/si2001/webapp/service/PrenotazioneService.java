package com.si2001.webapp.service;

import java.util.List;
import org.openapitools.model.PrenotazioneDTO;
import com.si2001.webapp.response.PrenotazioneResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PrenotazioneService {

	List<PrenotazioneDTO> getAllPrenotazioni();

	PrenotazioneResponse salvaPrenotazione(Long userId, PrenotazioneDTO prenotazioneDTO);

	void deletePrenotazione(Long prenotazioneId);

	List<PrenotazioneDTO> getPrenotazioniByUserId(Long userId);

	PrenotazioneResponse modificaPrenotazione(Long prenotazioneId, PrenotazioneDTO prenotazioneDTO);

	PrenotazioneDTO getPrenotazioneById(Long prenotazioneId);

	Page<PrenotazioneDTO> searchPrenotazioni(PrenotazioneDTO filter, List<String> columns, Pageable pageable);

	boolean existsById(Long prenotazioneId);

	Page<PrenotazioneDTO> getAllPrenotazioni(Pageable pageable);
}
