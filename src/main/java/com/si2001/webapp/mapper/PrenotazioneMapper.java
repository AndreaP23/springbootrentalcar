package com.si2001.webapp.mapper;

import com.si2001.webapp.entities.Prenotazione;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.openapitools.model.PrenotazioneDTO;


@Mapper(componentModel = "spring")
public interface PrenotazioneMapper {

    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "veicolo.veicoloId", target = "veicoloId")
    PrenotazioneDTO toDTO(Prenotazione prenotazione);

    @Mapping(source = "userId", target = "user.userId")
    @Mapping(source = "veicoloId", target = "veicolo.veicoloId")
    Prenotazione toEntity(PrenotazioneDTO prenotazioneDTO);
}

