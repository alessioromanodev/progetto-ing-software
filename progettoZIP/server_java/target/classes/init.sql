-- Script init.sql compatibile con H2 Database, con nomi di colonne in snake_case
-- (corrisponde ai nomi che il DAO si aspetta)

--------------------------------------------------------------------------------
-- 1. Creazione tabella utente
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

--------------------------------------------------------------------------------
-- 2. Creazione tabella ordine (inizialmente senza vincoli verso pagamento/consegna)
CREATE TABLE IF NOT EXISTS ordine (
    id INT PRIMARY KEY AUTO_INCREMENT,
    data_ordine TIMESTAMP NOT NULL,
    importo_totale DECIMAL(10,2) NOT NULL,
    metodo_consegna VARCHAR(50) NOT NULL,
    stato_ordine VARCHAR(50) NOT NULL,
    qr_code VARCHAR(200),
    id_utente INT NOT NULL,
    id_pagamento INT,
    id_consegna INT,
    CONSTRAINT fk_ordine_utente
      FOREIGN KEY (id_utente) REFERENCES utente(id)
    -- I vincoli su id_pagamento e id_consegna verranno aggiunti dopo
);

--------------------------------------------------------------------------------
-- 3. Creazione tabella fumetto (immagine_copertina impostata a NULL)
CREATE TABLE IF NOT EXISTS fumetto (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome_serie VARCHAR(100) NOT NULL,
    anno_serie VARCHAR(10),
    autore VARCHAR(100),
    numero_volume INT NOT NULL,
    titolo VARCHAR(200) NOT NULL,
    genere VARCHAR(50),
    casa_editrice VARCHAR(100),
    immagine_copertina BLOB DEFAULT NULL,
    descrizione CLOB,
    prezzo DECIMAL(10,2) NOT NULL,
    quantita_disponibile INT NOT NULL
);

--------------------------------------------------------------------------------
-- 4. Creazione tabella pagamento (fa riferimento a ordine.id)
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

--------------------------------------------------------------------------------
-- 5. Creazione tabella consegna (fa riferimento a ordine.id)
CREATE TABLE IF NOT EXISTS consegna (
    id_consegna INT PRIMARY KEY AUTO_INCREMENT,
    data_richiesta TIMESTAMP NOT NULL,
    data_consegna TIMESTAMP NOT NULL,
    stato VARCHAR(50) NOT NULL,
    id_ordine INT NOT NULL,
    CONSTRAINT fk_consegna_ordine
      FOREIGN KEY (id_ordine) REFERENCES ordine(id)
);

--------------------------------------------------------------------------------
-- 6. Creazione tabella riga_ordine (fa riferimento a ordine.id e fumetto.id)
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

--------------------------------------------------------------------------------
-- 7. Creazione tabella newsletter
CREATE TABLE IF NOT EXISTS newsletter (
    id_newsletter INT PRIMARY KEY AUTO_INCREMENT,
    titolo VARCHAR(200) NOT NULL,
    descrizione CLOB,
    data_creazione TIMESTAMP NOT NULL
);

--------------------------------------------------------------------------------
-- 8. Aggiunta dei vincoli circolari su ordine.id_pagamento e ordine.id_consegna
ALTER TABLE ordine
  ADD CONSTRAINT fk_ordine_pagamento
    FOREIGN KEY (id_pagamento) REFERENCES pagamento(id_pagamento);

ALTER TABLE ordine
  ADD CONSTRAINT fk_ordine_consegna
    FOREIGN KEY (id_consegna) REFERENCES consegna(id_consegna);

--------------------------------------------------------------------------------
-- 9. Inserimento dati di esempio

-- 9.1. Popolamento tabella utente
MERGE INTO utente (id, nome, cognome, username, role, password, email, indirizzo, registrato) KEY(id) VALUES
(1, 'Luca',   'Rossi',   'luca.rossi',    'cliente',       'pass123',    'luca.rossi@example.com',   'Via Roma 10, Milano',    TRUE),
(2, 'Giulia', 'Bianchi', 'giulia.bianchi','cliente',       'pwd456',     'giulia.bianchi@example.com','Corso Venezia 5, Roma', TRUE),
(3, 'Marco',  'Verdi',   'marco.verdi',   'amministratore','adm!n789',   'marco.verdi@example.com',  'Piazza Dante 2, Napoli', TRUE);

-- 9.2. Popolamento tabella fumetto
MERGE INTO fumetto (
    id, nome_serie, anno_serie, autore, numero_volume, titolo, genere,
    casa_editrice, immagine_copertina, descrizione, prezzo, quantita_disponibile
) KEY(id) VALUES
(1, 'Avengers',   '2021',         'Stan Lee',      1,  'The Coming of Ultron', 'Azione',    'Marvel',  NULL,
 'Il primo volumetto degli Avengers contro Ultron.',           9.99,  50),
(2, 'Batman',     '2019',         'Bob Kane',      2,  'Il Cavaliere Oscuro',  'Avventura', 'DC Comics', NULL,
 'Batman affronta Joker in una serie di avventure oscure.',       12.50, 30),
(3, 'One Piece',  '2020',         'Eiichiro Oda', 95, 'La Rotta per Raftel',  'Shonen',    'Shueisha', NULL,
 'Luffy e la ciurma si avvicinano al tesoro leggendario.',         7.80, 100);

-- 9.3. Popolamento tabella newsletter
MERGE INTO newsletter (id_newsletter, titolo, descrizione, data_creazione) KEY(id_newsletter) VALUES
(1, 'Novità di Giugno', 'Scopri i nuovi arrivi di fumetti per il mese di giugno 2025.', '2025-06-01 09:00:00'),
(2, 'Offerte Estive',   'Sconti speciali sui volumi estivi: approfittane ora!',                 '2025-06-02 12:00:00');

-- 9.4. Popolamento tabella ordine (inizialmente senza id_pagamento e id_consegna)
MERGE INTO ordine (
    id, data_ordine, importo_totale, metodo_consegna, stato_ordine, qr_code, id_utente, id_pagamento, id_consegna
) KEY(id) VALUES
(1, '2025-05-15 14:30:00', 22.48, 'Corriere Espresso', 'In lavorazione', 'QR123XYZ', 1, NULL, NULL),
(2, '2025-05-20 10:15:00',  7.80, 'Ritiro in negozio','Consegnato',     'QR456ABC', 2, NULL, NULL);

-- 9.5. Popolamento tabella pagamento (solo se l’ID non esiste già)
MERGE INTO pagamento (id_pagamento, data_pagamento, importo, metodo, sconto_applicato, id_ordine) KEY(id_pagamento) VALUES
(1, '2025-05-15 14:35:00', 22.48, 'Carta di Credito', FALSE, 1),
(2, '2025-05-20 10:20:00',  7.80, 'Contanti',         TRUE,  2);

-- 9.6. Aggiornamento tabella ordine per associare i pagamenti
UPDATE ordine
SET id_pagamento = 1
WHERE id = 1 AND id_pagamento IS NULL;
UPDATE ordine
SET id_pagamento = 2
WHERE id = 2 AND id_pagamento IS NULL;

-- 9.7. Popolamento tabella consegna (solo se l’ID non esiste già)
MERGE INTO consegna (id_consegna, data_richiesta, data_consegna, stato, id_ordine) KEY(id_consegna) VALUES
(1, '2025-05-15 15:00:00', '2025-05-18 12:00:00', 'Consegnato', 1),
(2, '2025-05-20 11:00:00', '2025-05-20 11:30:00', 'Consegnato', 2);

-- 9.8. Aggiornamento tabella ordine per associare le consegne
UPDATE ordine
SET id_consegna = 1
WHERE id = 1 AND id_consegna IS NULL;
UPDATE ordine
SET id_consegna = 2
WHERE id = 2 AND id_consegna IS NULL;

-- 9.9. Popolamento tabella riga_ordine (dettaglio articoli per ordine)
MERGE INTO riga_ordine (id, id_ordine, id_fumetto, quantita, prezzo_unitario) KEY(id) VALUES
(1, 1, 1, 1,  9.99),
(2, 1, 2, 1, 12.49),
(3, 2, 3, 1,  7.80);

--------------------------------------------------------------------------------
-- Fine dello script: tutte le tabelle vengono create con AUTO_INCREMENT dove necessario
-- e i dati di esempio inseriti con MERGE/UPDATE.
