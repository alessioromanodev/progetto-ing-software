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


MERGE INTO utente (id, nome, cognome, username, role, password, email, indirizzo, registrato) KEY(id) VALUES
  (1, 'Luca',   'Rossi',   'luca.rossi',    'cliente',       'pass123',    'luca.rossi@example.com',   'Via Roma 10, Milano',    TRUE),
  (2, 'Giulia', 'Bianchi', 'giulia.bianchi','cliente',       'pwd456',     'giulia.bianchi@example.com','Corso Venezia 5, Roma',   TRUE),
  (2, 'Giulia', 'Bianchi', 'giulia.bianchi','commesso',       'pwd456',     'giulia.bianchi@peppe.com','Corso Venezia 5, Roma',   TRUE),
  (3, 'Marco',  'Verdi',   'marco.verdi',   'amministratore','adm!n789',   'marco.verdi@example.com',  'Piazza Dante 2, Napoli', TRUE);

MERGE INTO fumetto (
    id, nome_serie, anno_serie, autore, numero_volume, titolo, genere,
    casa_editrice, immagine_copertina, descrizione, prezzo, quantita_disponibile
) KEY(id) VALUES
  (1, 'Avengers',   '2021',         'Stan Lee',         1,  'The Coming of Ultron',    'Azione',    'Marvel',
   'https://www.lafeltrinelli.it/images/9788828753131_0_0_536_0_75.jpg',
   'Il primo volumetto degli Avengers contro Ultron.',             9.99,  50),
  (2, 'Batman',     '2019',         'Bob Kane',         2,  'Il Cavaliere Oscuro',     'Avventura', 'DC Comics',
   'https://upload.wikimedia.org/wikipedia/it/1/19/Batman_3_variant.jpg',
   'Batman affronta Joker in una serie di avventure oscure.',       12.50, 30),
  (3, 'One Piece',  '2020',         'Eiichiro Oda',     95, 'La Rotta per Raftel',      'Shonen',    'Shueisha',
   'https://upload.wikimedia.org/wikipedia/it/5/5e/One_Piece_vol_1.jpg',
   'Luffy e la ciurma si avvicinano al tesoro leggendario.',         7.80, 100),
  (4, 'Naruto',     '2019',         'Masashi Kishimoto', 1, 'Il ninja nascosto',       'Shonen',    'Shueisha',
   'https://www.lafeltrinelli.it/images/9788828742470_0_0_0_0_0.jpg',
   'Primo volume di Naruto, ninja adolescente in crescita.',        6.00,  80),
  (5, 'Spider-Man', '2021',         'Stan Lee',         1, 'Il Ragno di Quartiere',    'Supereroi', 'Marvel',
   'https://giunti.it/cdn/shop/products/6f8d0d71954d4581a4ada237466d3de6.jpg?v=1748840957',
   'Peter Parker inizia la sua avventura da Spider-Man.',           8.50,  60),
  (6, 'Superman',   '2021',         'Jerry Siegel',     1, 'Uomo di ferro',            'Supereroi', 'DC Comics',
   'https://m.media-amazon.com/images/I/61CWMUMf3ML._AC_UF1000,1000_QL80_.jpg',
   'Clark Kent scopre i suoi poteri e diventa Superman.',            10.00, 40),
  (7, 'X-Men',      '2020',         'Stan Lee',         1, 'Genesi Mutante',           'Supereroi', 'Marvel',
   'https://www.lafeltrinelli.it/images/9788828753100_0_0_536_0_75.jpg',
   'Gli X-Men si uniscono per proteggere i mutanti.',              11.50, 25),
  (8, 'Dragon Ball', '1984',        'Akira Toriyama',   1, 'La nascita di Goku',       'Shonen',    'Shueisha',
   'https://upload.wikimedia.org/wikipedia/it/thumb/3/3f/Dragon_Ball_cover_1.jpg/960px-Dragon_Ball_cover_1.jpg',
   'Primo volume della serie Dragon Ball con il giovane Goku.',     5.99,  90),
  (9, 'Fullmetal Alchemist','2001', 'Hiromu Arakawa',   1, 'Alchimia e Fratellanza',   'Shonen',    'Square Enix',
   'https://m.media-amazon.com/images/I/819gbwpjLcL.jpg',
   'Due fratelli alchimisti cercano la Pietra Filosofale.',         7.50,  70),
  (10,'Death Note', '2003',         'Tsugumi Ohba',     1, 'Il Quaderno della Morte',  'Thriller',  'Shueisha',
   'https://upload.wikimedia.org/wikipedia/it/0/08/Death_Note_volume_01.jpg',
   'Light Yagami trova un quaderno che può uccidere chiunque.',     6.99,  65),
  (11,'Attacco Dei Giganti','2009','Hajime Isayama',   1, 'Attacco dei Giganti',      'Seinen',    'Kodansha',
   'https://m.media-amazon.com/images/I/71fxbLLBJ-L._AC_UF1000,1000_QL80_.jpg',
   'Umani combattono contro i giganti per sopravvivere.',           8.00,  50),
  (12,'Bleach',     '2001',         'Tite Kubo',        1, 'Il Potere dello Spettro',  'Shonen',    'Shueisha',
   'https://upload.wikimedia.org/wikipedia/it/e/ed/Bleachcopertina.jpg',
   'Ichigo Kurosaki diventa un Dio della Morte sostituo.',           6.50,  55),
  (13,'Jujutsu Kaisen','2018',      'Gege Akutami',     1, 'Malédizione Oscura',       'Shonen',    'Shueisha',
   'https://m.media-amazon.com/images/I/81TmHlRleJL.jpg',
   'Yuji Itadori combatte le maledizioni usando poteri soprannaturali.', 7.95, 45),
  (14,'My Hero Academia','2014',    'Kohei Horikoshi',  1, 'Il Nuovo Eroe',            'Shonen',    'Shueisha',
   'https://upload.wikimedia.org/wikipedia/it/1/1a/My_Hero_Academia_manga.jpg',
   'Izuku Midoriya sogna di diventare un eroe in un mondo di superpoteri.', 7.20,  60),
  (15,'Demon Slayer','2016',        'Koyoharu Gotouge', 1, 'La Spada del Demone',       'Shonen',    'Shueisha',
   'https://m.media-amazon.com/images/I/81SSgB6ZEJL.jpg',
   'Tanjiro Kamado lotta contro i demoni per salvare sua sorella.',   8.25,  50),
  (16,'Tokyo Ghoul','2011',         'Sui Ishida',       1, 'Un Nuovo Inizio',           'Seinen',    'Shueisha',
   'https://m.media-amazon.com/images/I/71SrizbdxPL._AC_UF1000,1000_QL80_.jpg',
   'Ken Kaneki diventa mezzo-ghoul dopo un incidente drammatico.',   7.00,  40),
  (17,'Fairy Tail','2006',          'Hiro Mashima',     1, 'Gilda di Maghi',            'Shonen',    'Kodansha',
   'https://m.media-amazon.com/images/I/81NswPCa5JL._AC_UF1000,1000_QL80_.jpg',
   'Lucy Heartfilia entra a far parte della gilda di Fairy Tail.',    6.80,  55),
  (18,'Hunter x Hunter','1998',     'Yoshihiro Togashi',1, 'Il Mondo di Hunters',       'Shonen',    'Shueisha',
   'https://upload.wikimedia.org/wikipedia/it/7/77/Hunter_X_Hunter.jpg',
   'Gon Freecss parte alla ricerca del padre e diventa Hunter.',     7.75,  45),
  (19,'Black Clover','2015',        'Yuki Tabata',      1, 'I Gemelli Magici',          'Shonen',    'Shueisha',
   'https://upload.wikimedia.org/wikipedia/it/thumb/d/dd/BlackClover1.jpg/640px-BlackClover1.jpg',
   'Asta e Yuno crescono in un mondo dove la magia è tutto.',         7.90,  40),
  (20,'Naruto Shippuden','2007',     'Masashi Kishimoto',1, 'Ritorno del Ninja',         'Shonen',    'Shueisha',
   'https://m.media-amazon.com/images/M/MV5BNTk3MDA1ZjAtNTRhYS00YzNiLTgwOGEtYWRmYTQ3NjA0NTAwXkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg',
   'Naruto torna a Konoha dopo allenamenti intensi.',                6.00,  55);

MERGE INTO newsletter (id_newsletter, titolo, descrizione, data_creazione) KEY(id_newsletter) VALUES
  (1, 'Novità di Giugno', 'Scopri i nuovi arrivi di fumetti per il mese di giugno 2025.', '2025-06-01 09:00:00'),
  (2, 'Offerte Estive',   'Sconti speciali sui volumi estivi: approfittane!',                 '2025-06-02 12:00:00');

MERGE INTO ordine (
    id, data_ordine, importo_totale, metodo_consegna, stato_ordine, qr_code, id_utente) KEY(id) VALUES
  (1, '2025-05-15 14:30:00', 22.48, 'Corriere Espresso', 'In lavorazione',   'QR123XYZ', 1),
  (2, '2025-05-20 10:15:00',  7.80, 'Ritiro in negozio', 'Consegnato',       'QR456ABC', 2);

MERGE INTO pagamento (id_pagamento, data_pagamento, importo, metodo, sconto_applicato, id_ordine) KEY(id_pagamento) VALUES
  (1, '2025-05-15 14:35:00', 22.48, 'Carta di Credito', FALSE, 1),
  (2, '2025-05-20 10:20:00',  7.80, 'Contanti',         TRUE,  2);

MERGE INTO consegna (id_consegna, data_richiesta, data_consegna, stato, id_ordine) KEY(id_consegna) VALUES
  (1, '2025-05-15 15:00:00', '2025-05-18 12:00:00', 'Consegnato', 1),
  (2, '2025-05-20 11:00:00', '2025-05-20 11:30:00', 'Consegnato', 2);


MERGE INTO riga_ordine (id, id_ordine, id_fumetto, quantita, prezzo_unitario) KEY(id) VALUES
  (1, 1, 1, 1,  9.99),
  (2, 1, 2, 1, 12.49),
  (3, 2, 3, 1,  7.80);
