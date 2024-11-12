-- Inserisci nuovi dati nella tabella "user"
INSERT INTO "user" (user_id, cognome, data_nascita, email, nome, password, ruolo_id, phone)
VALUES
    (1, 'Blu', '1993-07-18', 'federico.blu@example.com', 'Federico', 'password303', 2, '6789012345'),
    (2, 'Tre', '1990-01-01', 'ilenia@live.it', 'Ilenia', 'password123', 2, '1234567890');

-- Inserisci nuovi dati nella tabella veicolo
INSERT INTO veicolo (veicolo_id, anno, disponibilita, marca, modello, targa)
VALUES
    (1, 2018, 1, 'Ford', 'Focus', 'BB234CC');

-- Inserisci nuovi dati nella tabella prenotazione
INSERT INTO prenotazione (prenotazione_id, data_fine, data_inizio, data_prenotazione, user_id, veicolo_id, note)
VALUES
    (1, '2024-11-19', '2024-11-15', '2024-10-10', 1, 1, 'Nota di prova');

-- Resetta il contatore di ID
ALTER TABLE prenotazione ALTER COLUMN prenotazione_id RESTART WITH 2;
