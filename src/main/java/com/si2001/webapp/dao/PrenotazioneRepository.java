package com.si2001.webapp.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.si2001.webapp.entities.Prenotazione;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long>, JpaSpecificationExecutor<Prenotazione> {

	Prenotazione findByPrenotazioneId(Long prenotazioneId);
	List<Prenotazione> findByUserUserId(Long userId);
	//@Query("SELECT p FROM Prenotazione p WHERE p.user.userId = :userId")
	Page<Prenotazione> findByUser_UserId(Long userId, Pageable pageable);
	//_ mi fa accedere ad una relazione

}
