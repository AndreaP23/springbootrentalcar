CREATE TABLE IF NOT EXISTS "user" (
                                      user_id BIGINT PRIMARY KEY,
                                      cognome VARCHAR(255),
    data_nascita DATE,
    email VARCHAR(255),
    nome VARCHAR(255),
    password VARCHAR(255),
    ruolo_id INT,
    phone VARCHAR(255)
    );


CREATE TABLE IF NOT EXISTS veicolo (
                                       veicolo_id BIGINT PRIMARY KEY,
                                       anno INT,
                                       disponibilita INT,
                                       marca VARCHAR(255),
    modello VARCHAR(255),
    targa VARCHAR(255) UNIQUE
    );

CREATE TABLE IF NOT EXISTS prenotazione (
                                            prenotazione_id BIGINT PRIMARY KEY,
                                            data_fine DATE,
                                            data_inizio DATE,
                                            data_prenotazione DATE,
                                            user_id BIGINT,
                                            veicolo_id BIGINT,
                                            note VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES "user"(user_id),
    FOREIGN KEY (veicolo_id) REFERENCES veicolo(veicolo_id)
    );
