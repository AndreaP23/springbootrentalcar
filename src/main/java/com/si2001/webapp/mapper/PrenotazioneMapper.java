package com.si2001.webapp.mapper;

import com.si2001.webapp.dto.PrenotazioneDTO;
import com.si2001.webapp.entities.Prenotazione;
import org.springframework.stereotype.Component;

@Component
public class PrenotazioneMapper {

    public PrenotazioneDTO toDTO(Prenotazione prenotazione) {
        if (prenotazione == null) {
            return null;
        }

        PrenotazioneDTO.PrenotazioneDTOBuilder dtoBuilder = PrenotazioneDTO.builder()
                .prenotazioneId(prenotazione.getPrenotazioneId())
                .dataPrenotazione(prenotazione.getDataPrenotazione())
                .dataInizio(prenotazione.getDataInizio())
                .dataFine(prenotazione.getDataFine())
                .userId(prenotazione.getUser().getUserId())
                .note(prenotazione.getNote());

        if (prenotazione.getVeicolo() != null) {
            dtoBuilder.veicoloId(prenotazione.getVeicolo().getVeicoloId());
        } else {
            dtoBuilder.veicoloId(null); 
        }

        return dtoBuilder.build();
    }

    public Prenotazione toEntity(PrenotazioneDTO dto) {
        if (dto == null) {
            return null;
        }

        Prenotazione prenotazione = new Prenotazione();


        return prenotazione;
    }
}
