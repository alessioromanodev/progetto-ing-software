CREATE TABLE IF NOT EXISTS utente (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    indirizzo VARCHAR(200),
    registrato BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS ordine (
    id INT PRIMARY KEY AUTO_INCREMENT,
    data_ordine TIMESTAMP NOT NULL,
    importo_totale DECIMAL(10,2) NOT NULL,
    metodo_consegna VARCHAR(50) NOT NULL,
    stato_ordine VARCHAR(50) NOT NULL,
    qr_code VARCHAR(200),
    id_utente INT NOT NULL,
    CONSTRAINT fk_ordine_utente
      FOREIGN KEY (id_utente) REFERENCES utente(id)
);

CREATE TABLE IF NOT EXISTS fumetto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome_serie VARCHAR(100) NOT NULL,
    anno_serie VARCHAR(10),
    autore VARCHAR(100),
    numero_volume INT NOT NULL,
    titolo VARCHAR(200) NOT NULL,
    genere VARCHAR(50),
    casa_editrice VARCHAR(100),
    immagine_copertina VARCHAR(200) DEFAULT NULL,
    descrizione CLOB,
    prezzo DECIMAL(10,2) NOT NULL,
    quantita_disponibile INT NOT NULL
);

CREATE TABLE IF NOT EXISTS pagamento (
    id_pagamento INT PRIMARY KEY AUTO_INCREMENT,
    data_pagamento TIMESTAMP NOT NULL,
    importo DECIMAL(10,2) NOT NULL,
    metodo VARCHAR(50) NOT NULL,
    sconto_applicato BOOLEAN NOT NULL,
    id_ordine INT NOT NULL,
    CONSTRAINT fk_pagamento_ordine
      FOREIGN KEY (id_ordine) REFERENCES ordine(id)
);

CREATE TABLE IF NOT EXISTS consegna (
    id_consegna INT PRIMARY KEY AUTO_INCREMENT,
    data_richiesta TIMESTAMP NOT NULL,
    data_consegna TIMESTAMP NOT NULL,
    stato VARCHAR(50) NOT NULL,
    id_ordine INT NOT NULL,
    CONSTRAINT fk_consegna_ordine
      FOREIGN KEY (id_ordine) REFERENCES ordine(id)
);

CREATE TABLE IF NOT EXISTS riga_ordine (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_ordine INT NOT NULL,
    id_fumetto INT NOT NULL,
    quantita INT NOT NULL,
    prezzo_unitario DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_riga_ordine_ordine
      FOREIGN KEY (id_ordine) REFERENCES ordine(id),
    CONSTRAINT fk_riga_ordine_fumetto
      FOREIGN KEY (id_fumetto) REFERENCES fumetto(id)
);

CREATE TABLE IF NOT EXISTS newsletter (
    id_newsletter INT PRIMARY KEY AUTO_INCREMENT,
    titolo VARCHAR(200) NOT NULL,
    descrizione CLOB,
    data_creazione TIMESTAMP NOT NULL
);
