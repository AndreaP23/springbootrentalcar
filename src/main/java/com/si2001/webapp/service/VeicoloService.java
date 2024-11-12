package com.si2001.webapp.service;

import org.openapitools.model.VeicoloDTO;

import java.util.List;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.openapitools.model.VeicoloDTO;

public interface VeicoloService {
	Page<VeicoloDTO> getAllVeicoli(Pageable pageable);
}
