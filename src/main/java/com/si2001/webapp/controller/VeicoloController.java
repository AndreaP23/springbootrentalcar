package com.si2001.webapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.si2001.webapp.dto.VeicoloDTO;
import com.si2001.webapp.service.VeicoloService;

@RestController
public class VeicoloController {
	
	@Autowired
	private VeicoloService veicoloService;

    @GetMapping("/listveicoli")
    public List<VeicoloDTO> showVeicolo() {
        return veicoloService.getAllVeicoli();
    }
}
