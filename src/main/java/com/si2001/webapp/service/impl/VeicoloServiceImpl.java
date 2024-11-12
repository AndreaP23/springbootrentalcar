package com.si2001.webapp.service.impl;

import com.si2001.webapp.dao.VeicoloRepository; // Assicurati di avere questo repository
import com.si2001.webapp.entities.Veicolo;
import com.si2001.webapp.mapper.VeicoloMapper;
import com.si2001.webapp.service.VeicoloService;
import org.openapitools.model.VeicoloDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VeicoloServiceImpl implements VeicoloService {

    @Autowired
    private VeicoloRepository veicoloRepository;

    @Autowired
    private VeicoloMapper veicoloMapper;

    @Override
    public Page<VeicoloDTO> getAllVeicoli(Pageable pageable) {
        return veicoloRepository.findAll(pageable)
                .map(veicolo -> veicoloMapper.toDTO(veicolo));
    }
}
