package com.si2001.webapp.mapper;

import com.si2001.webapp.dto.VeicoloDTO;
import com.si2001.webapp.entities.Veicolo;
import org.springframework.stereotype.Component;

@Component
public class VeicoloMapper {

    public VeicoloDTO toDTO(Veicolo veicolo) {
        if (veicolo == null) {
            return null;
        }
        

       return VeicoloDTO.builder()
                .veicoloId(veicolo.getVeicoloId())
                .marca(veicolo.getMarca())
                .modello(veicolo.getModello())
                .anno(veicolo.getAnno())
                .targa(veicolo.getTarga())
                .disponibilita(veicolo.isDisponibilita())
                .build();
    }

    public Veicolo toEntity(VeicoloDTO dto) {
        if (dto == null) {
            return null;
        }


        return Veicolo.builder()
                .veicoloId(dto.getVeicoloId())
                .marca(dto.getMarca())
                .modello(dto.getModello())
                .anno(dto.getAnno())
                .targa(dto.getTarga())
                .disponibilita(dto.isDisponibilita())
                .build();
    }
}
