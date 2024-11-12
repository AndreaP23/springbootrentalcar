package com.si2001.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.openapitools.api.VeicoliApi;
import org.openapitools.model.VeicoloDTO;
import org.openapitools.model.ListVeicoli200Response;
import com.si2001.webapp.service.VeicoloService;

@RestController
public class VeicoloController implements VeicoliApi {

    @Autowired
    private VeicoloService veicoloService;

    @Override
    public ResponseEntity<ListVeicoli200Response> listVeicoli(Integer page, Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 10;

        Pageable pageable = PageRequest.of(page, size);

        // Ottieni la pagina di veicoli dal servizio
        Page<VeicoloDTO> veicoliPage = veicoloService.getAllVeicoli(pageable);

        // Costruisci la risposta con ListVeicoli200Response
        ListVeicoli200Response response = new ListVeicoli200Response();
        response.setContent(veicoliPage.getContent());
        response.setTotalPages(veicoliPage.getTotalPages());
        response.setTotalElements((int) veicoliPage.getTotalElements());
        response.setSize(veicoliPage.getSize());
        response.setNumber(veicoliPage.getNumber());

        return ResponseEntity.ok(response);
    }
}
