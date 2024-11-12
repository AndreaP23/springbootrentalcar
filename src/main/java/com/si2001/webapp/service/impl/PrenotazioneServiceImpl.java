package com.si2001.webapp.service.impl;

import com.si2001.webapp.entities.Prenotazione;
import com.si2001.webapp.entities.Veicolo;
import com.si2001.webapp.mapper.PrenotazioneMapper;
import com.si2001.webapp.dao.PrenotazioneRepository;
import com.si2001.webapp.dao.UserRepository;
import com.si2001.webapp.dao.VeicoloRepository;
import com.si2001.webapp.response.PrenotazioneNotFoundException;
import com.si2001.webapp.response.PrenotazioneResponse;
import com.si2001.webapp.service.PrenotazioneService;
import com.si2001.webapp.specification.PrenotazioneSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.openapitools.model.PrenotazioneDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PrenotazioneServiceImpl implements PrenotazioneService {

    //Dichiarazione dipende ed utilizzo
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

        // Converte il veicoloId da Integer a Long
        Long veicoloId = prenotazioneDTO.getVeicoloId().longValue();

        // Trova il veicolo
        Veicolo veicolo = veicoloRepository.findById(veicoloId)
                .orElseThrow(() -> new IllegalArgumentException("Veicolo non trovato"));

        if (veicolo.getDisponibilita() != 1) {
            response.setValidated(false);
            response.setErrorMessages(Map.of("general", "Veicolo non disponibile per la prenotazione."));
            return response;
        }

        // Usa il mapper per convertire DTO in entità
        Prenotazione prenotazione = prenotazioneMapper.toEntity(prenotazioneDTO);

        // Imposta utente e dettagli della prenotazione
        prenotazione.setUser(userRepository.findById(userId).orElseThrow());
        prenotazione.setDataPrenotazione(LocalDate.now());
        prenotazione.setVeicolo(veicolo);

        // Salva prenotazione
        prenotazioneRepository.save(prenotazione);

        // Aggiorna la disponibilità del veicolo
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
                .orElseThrow(() -> new PrenotazioneNotFoundException("Prenotazione con ID " + prenotazioneId + " non trovata"));

        return prenotazioneMapper.toDTO(prenotazione);
    }

    @Override
    public Page<PrenotazioneDTO> searchPrenotazioni(PrenotazioneDTO filter, List<String> columns, Pageable pageable) {
        Specification<Prenotazione> specification = PrenotazioneSpecification.getPrenotazioneFilter(filter);

        Page<Prenotazione> prenotazioni = prenotazioneRepository.findAll(specification, pageable);

        return prenotazioni.map(prenotazioneMapper::toDTO);
    }

    public boolean existsById(Long prenotazioneId) {
        return prenotazioneRepository.existsById(prenotazioneId);
    }

    //Come paginare, ma ho deciso di fare tutto in searchPrenotazioni
    @Override
    public Page<PrenotazioneDTO> getAllPrenotazioni(Pageable pageable) {
        return prenotazioneRepository.findAll(pageable)
                .map(user -> prenotazioneMapper.toDTO(user));
    }
}

