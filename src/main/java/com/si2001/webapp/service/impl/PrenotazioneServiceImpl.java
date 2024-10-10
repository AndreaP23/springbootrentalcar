package com.si2001.webapp.service.impl;

import com.si2001.webapp.dto.PrenotazioneDTO;
import com.si2001.webapp.entities.Prenotazione;
import com.si2001.webapp.entities.User;
import com.si2001.webapp.entities.Veicolo;
import com.si2001.webapp.mapper.PrenotazioneMapper;
import com.si2001.webapp.dao.PrenotazioneRepository;
import com.si2001.webapp.dao.UserRepository;
import com.si2001.webapp.dao.VeicoloRepository;
import com.si2001.webapp.response.PrenotazioneResponse;
import com.si2001.webapp.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PrenotazioneServiceImpl implements PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private VeicoloRepository veicoloRepository;

    @Autowired
    private UserRepository userRepository; 

    @Autowired
    private PrenotazioneMapper prenotazioneMapper; 

    @Override
    public PrenotazioneResponse salvaPrenotazione(Long userId, PrenotazioneDTO prenotazioneDTO) {
        PrenotazioneResponse response = new PrenotazioneResponse();

        Veicolo veicolo = veicoloRepository.findById(prenotazioneDTO.getVeicoloId())
                .orElseThrow(() -> new IllegalArgumentException("Veicolo non trovato"));

        if (veicolo.getDisponibilita() != 1) {
            response.setValidated(false);
            response.setErrorMessages(Map.of("general", "Veicolo non disponibile per la prenotazione."));
            return response;
        }


        Prenotazione prenotazione = prenotazioneMapper.toEntity(prenotazioneDTO);
        
        prenotazione.setUser(userRepository.findById(userId).orElseThrow());
        prenotazione.setDataPrenotazione(LocalDate.now()); 
        prenotazione.setDataInizio(prenotazioneDTO.getDataInizio());
        prenotazione.setDataFine(prenotazioneDTO.getDataFine());
        prenotazione.setVeicolo(veicolo);
        prenotazione.setNote(prenotazioneDTO.getNote());
        prenotazioneRepository.save(prenotazione);

        veicolo.setDisponibilita(0); 
        veicoloRepository.save(veicolo);

        response.setValidated(true);
        response.setSuccessMessage("Prenotazione salvata con successo.");
        return response;
    }


    @Override
    public List<PrenotazioneDTO> getAllPrenotazioni() {
        List<Prenotazione> prenotazioni = prenotazioneRepository.findAll(); 
        return prenotazioni.stream()
                .map(prenotazioneMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public void deletePrenotazione(Long prenotazioneId) {
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new IllegalArgumentException("Prenotazione non trovata"));

        Veicolo veicolo = prenotazione.getVeicolo();
        
        veicolo.setDisponibilita(1); 
        veicoloRepository.save(veicolo);


        prenotazioneRepository.delete(prenotazione);
    }



	@Override
	public List<PrenotazioneDTO> getPrenotazioniByUserId(Long userId) {
	    List<Prenotazione> prenotazioni = prenotazioneRepository.findByUserUserId(userId);
	    return prenotazioni.stream()
	        .map(prenotazioneMapper::toDTO)
	        .collect(Collectors.toList());
	}


	@Override
	public PrenotazioneResponse modificaPrenotazione(Long prenotazioneId, PrenotazioneDTO prenotazioneDTO) {
	    PrenotazioneResponse response = new PrenotazioneResponse();

	    Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
	            .orElseThrow(() -> new IllegalArgumentException("Prenotazione non trovata"));

	    prenotazione.setDataInizio(prenotazioneDTO.getDataInizio());
	    prenotazione.setDataFine(prenotazioneDTO.getDataFine());
	    prenotazione.setNote(prenotazioneDTO.getNote());

	    prenotazioneRepository.save(prenotazione);
	    
	    response.setValidated(true);
	    response.setSuccessMessage("Prenotazione modificata con successo.");
	    return response;
	}
	
	@Override
    public PrenotazioneDTO getPrenotazioneById(Long prenotazioneId) {
        Prenotazione prenotazione = prenotazioneRepository.findById(prenotazioneId)
                .orElseThrow(() -> new IllegalArgumentException("Prenotazione non trovata"));
        return prenotazioneMapper.toDTO(prenotazione);
    }


}