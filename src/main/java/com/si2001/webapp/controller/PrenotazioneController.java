package com.si2001.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.si2001.webapp.dto.PrenotazioneDTO;
import com.si2001.webapp.service.PrenotazioneService;

@RestController
public class PrenotazioneController {
	@Autowired
	private PrenotazioneService prenotazioneService;
	
	@GetMapping("/listprenotazioni")
	public List<PrenotazioneDTO> showPrenotazioni(){
		return prenotazioneService.getAllPrenotazioni();
	}

}
