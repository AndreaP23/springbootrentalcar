package com.si2001.webapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.si2001.webapp.entities.Prenotazione;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Long> {
	
	Prenotazione findByPrenotazioneId(Long prenotazioneId);
}
