package com.si2001.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VeicoloDTO {

    private Long veicoloId;
    private String marca;
    private String modello;
    private int anno;
    private String targa;
    private boolean disponibilita;
    
}
