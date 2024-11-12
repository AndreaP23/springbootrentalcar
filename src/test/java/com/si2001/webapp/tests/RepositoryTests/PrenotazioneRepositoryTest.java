package com.si2001.webapp.tests.RepositoryTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.si2001.webapp.dao.PrenotazioneRepository;
import com.si2001.webapp.entities.Prenotazione;
import com.si2001.webapp.entities.User;
import com.si2001.webapp.entities.Veicolo;



@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PrenotazioneRepositoryTest {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Test
    @Order(1)
    public void TestInsPrenotazione() {
        User user = new User();
        user.setUserId(9L);
        user.setNome("Luca");
        user.setCognome("Verdi");

        Veicolo veicolo = new Veicolo();
        veicolo.setVeicoloId(1L);
        veicolo.setMarca("Fiat");
        veicolo.setModello("Punto");

        // Crea una nuova prenotazione
        Prenotazione prenotazione = Prenotazione.builder()
                .user(user)
                .veicolo(veicolo)
                .dataPrenotazione(LocalDate.now())
                .dataInizio(LocalDate.of(2024, 10, 20))
                .dataFine(LocalDate.of(2024, 10, 25))
                .note("Prenotazione di test")
                .build();

        prenotazioneRepository.save(prenotazione);

        assertThat(prenotazioneRepository.findByPrenotazioneId(prenotazione.getPrenotazioneId()))
                .extracting(Prenotazione::getNote)
                .isEqualTo("Prenotazione di test");
    }

    @Transactional //By using @Transactional, many important aspects such as transaction propagation are handled automatically. In this case if another transactional method is called the method will have the option of joining the ongoing transaction avoiding the "no session" exception.
    @Test
    @Order(2)
    public void TestfindByUserId() {
        // Recupera le prenotazioni per l'utente con ID 9
        List<Prenotazione> prenotazioni = prenotazioneRepository.findByUserUserId(9L);
        assertEquals(3, prenotazioni.size());
        assertThat(prenotazioni.get(0).getUser().getNome()).isEqualTo("Luca");
    }

    @Transactional
    @Test
    @Order(3)
    public void TestfindByPrenotazioneId() {
        // Recupera la prenotazione specifica tramite il suo ID (assicurati che l'ID esista)
        Prenotazione prenotazione = prenotazioneRepository.findByPrenotazioneId(18L);
        assertThat(prenotazione).isNotNull();
        assertThat(prenotazione.getUser().getNome()).isEqualTo("Sheena");
    }

    /*@Test
    @Order(4)
    public void TestDelPrenotazione() {
        Prenotazione prenotazione = prenotazioneRepository.findByPrenotazioneId(16L);
        prenotazioneRepository.delete(prenotazione);

        assertThat(prenotazioneRepository.findByPrenotazioneId(16L)).isNull();
    }*/
}
