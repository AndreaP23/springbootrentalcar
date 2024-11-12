package com.si2001.webapp.tests.ControllerTests;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.si2001.webapp.Application;

import com.si2001.webapp.dto.PrenotazioneDTO;
import com.si2001.webapp.service.PrenotazioneService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Application.class)
@ActiveProfiles("test")
@SpringBootTest
@Sql(scripts = "classpath:data.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PrenotazioneControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private PrenotazioneService prenotazioneService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testGetAllPrenotazione() throws Exception {
        mockMvc.perform(get("/listprenotazioni")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetPrenotazioniPaginata() throws Exception {
        mockMvc.perform(get("/listprenotazioniPaginata")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetPrenotazioniByUserId() throws Exception {
        mockMvc.perform(get("/listprenotazioni/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].prenotazioneId").exists())
                .andDo(print());
    }

    @Test
    public void testGetPrenotazioneById() throws Exception {
        mockMvc.perform(get("/listabyprenotazione/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prenotazioneId").value(1L));
    }

    @Test
    public void testPrenotazioneNonTrovata() throws Exception {
        mockMvc.perform(get("/listprenotazioni/9999")  // ID inesistente
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void testGetPrenotazioneByIdNotFound() throws Exception {
        // Configura il comportamento del servizio per restituire null (prenotazione non trovata)
        when(prenotazioneService.getPrenotazioneById(9999L)).thenReturn(null);

        // Esegui la richiesta GET e verifica il risultato
        mockMvc.perform(MockMvcRequestBuilders.get("/listabyprenotazione/9999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testModificaPrenotazione() throws Exception {
        PrenotazioneDTO prenotazioneDTO = PrenotazioneDTO.builder()
                .userId(1L)
                .veicoloId(2L)
                .dataInizio(LocalDate.of(2024, 11, 1))
                .dataFine(LocalDate.of(2024, 11, 10))
                .note("Modifica Nota")
                .build();

        mockMvc.perform(put("/modifica/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prenotazioneDTO)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testModificaPrenotazioneNonTrovata() throws Exception {
        PrenotazioneDTO prenotazioneDTO = PrenotazioneDTO.builder()
                .userId(1L)
                .veicoloId(2L)
                .dataInizio(LocalDate.of(2024, 11, 1))
                .dataFine(LocalDate.of(2024, 11, 10))
                .note("Modifica note")
                .build();

        mockMvc.perform(put("/modifica/9999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prenotazioneDTO)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testModificaPrenotazioneDatiNonValidi() throws Exception {
        PrenotazioneDTO prenotazioneDTO = PrenotazioneDTO.builder()
                .userId(1L)
                .veicoloId(999L)
                .dataInizio(LocalDate.of(2024, 11, 1))
                .dataFine(LocalDate.of(2024, 11, 10))
                .note("Modifica nota non valida")
                .build();

        mockMvc.perform(put("/modifica/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prenotazioneDTO)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void testSalvaPrenotazione() throws Exception {
        PrenotazioneDTO prenotazioneDTO = PrenotazioneDTO.builder()
                .userId(1L)
                .veicoloId(1L)
                .dataPrenotazione(LocalDate.of(2024, 11, 1))
                .dataInizio(LocalDate.of(2024, 10, 25))
                .dataFine(LocalDate.of(2024, 10, 30))
                .note("Test note")
                .build();

        mockMvc.perform(post("/salva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prenotazioneDTO)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void testSalvaPrenotazioneSenzaUserId() throws Exception {
        PrenotazioneDTO prenotazioneDTO = PrenotazioneDTO.builder()
                .veicoloId(1L)
                .dataInizio(LocalDate.of(2024, 10, 25))
                .dataFine(LocalDate.of(2024, 10, 30))
                .note("Test note")
                .build();

        mockMvc.perform(post("/salva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prenotazioneDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }


    //Il messaggio deve essere contenuto
    @Test
    public void testSalvaPrenotazioneDatiMancanti() throws Exception {
        PrenotazioneDTO prenotazioneDTO = PrenotazioneDTO.builder()
                .userId(5L)
                .veicoloId(1L)
                .dataInizio(null)
                .dataFine(LocalDate.of(2024, 10, 30))
                .note("Test note senza data inizio")
                .build();

        mockMvc.perform(post("/salva")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prenotazioneDTO)))
                .andExpect(status().isBadRequest())
                //.andExpect(jsonPath("$.error").value("Dati mancanti o non validi"))
                .andDo(print());
    }

    @Test
    public void testDeletePrenotazione() throws Exception {
        mockMvc.perform(delete("/superuser/deletePrenotazione/1"))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.message").value("Prenotazione eliminata con successo."))
                .andDo(print());
    }

    @Test
    public void testDeletePrenotazioneNonTrovata() throws Exception {
        mockMvc.perform(delete("/superuser/deletePrenotazione/9999"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testDeletePrenotazioneErrore() throws Exception {
        doThrow(new RuntimeException("Errore: Prenotazione non trovata o non eliminata."))
                .when(prenotazioneService).deletePrenotazione(anyLong());

        mockMvc.perform(delete("/superuser/deletePrenotazione/10"))
                .andExpect(status().isNotFound())
                //.andExpect(jsonPath("$.error").value("Prenotazione non trovata o già eliminata."))
                .andDo(print());
    }

}
