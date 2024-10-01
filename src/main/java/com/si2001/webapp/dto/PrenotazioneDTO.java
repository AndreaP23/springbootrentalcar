package com.si2001.webapp.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrenotazioneDTO {

    private Long prenotazioneId;
    private Long userId;     
    private Long veicoloId;  
    private LocalDate dataInizio;
    private LocalDate dataFine;
}
