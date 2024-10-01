package com.si2001.webapp.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private Long userId;
    private String nome;
    private String cognome;
    private String email;
    private String telefono;
    private String password; 
    private LocalDate dataNascita;
    private Integer ruolo;
    private List<Long> prenotazioniIds; 
}
