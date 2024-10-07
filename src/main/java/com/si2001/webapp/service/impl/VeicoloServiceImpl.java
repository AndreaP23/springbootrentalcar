package com.si2001.webapp.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.si2001.webapp.dao.VeicoloRepository;
import com.si2001.webapp.dto.VeicoloDTO;
import com.si2001.webapp.entities.Veicolo;
import com.si2001.webapp.mapper.VeicoloMapper;
import com.si2001.webapp.service.VeicoloService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class VeicoloServiceImpl implements VeicoloService {
	 
	private static final Logger logger = LoggerFactory.getLogger(VeicoloServiceImpl.class);
	private final VeicoloRepository veicoloRepository;
	private final VeicoloMapper veicoloMapper;
	
	//Posso togliere questo costruttore
	/*public VeicoloServiceImpl(VeicoloRepository veicoloRepository) {
		this.veicoloRepository = veicoloRepository;
	}*/
	
    @Override
    public List<VeicoloDTO> getAllVeicoli() {
        List<Veicolo> veicoli = veicoloRepository.findAll();
        return veicoli.stream()
                .map(veicoloMapper::toDTO) 
                .collect(Collectors.toList());
    }
	
	//creare mapper a parte
    /*
	private VeicoloDTO mapToVeicoloDTO(Veicolo veicolo) {
	    VeicoloDTO dto = new VeicoloDTO();
	    dto.setVeicoloId(veicolo.getVeicoloId());
	    dto.setMarca(veicolo.getMarca());
	    dto.setModello(veicolo.getModello());
	    dto.setAnno(veicolo.getAnno());
	    dto.setTarga(veicolo.getTarga());
	    dto.setDisponibilita(veicolo.isDisponibilita());
	    return dto;
	}*/


}