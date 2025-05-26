CREATE TABLE fumetti (
    id_fumetto INT PRIMARY KEY AUTO_INCREMENT,
    nome_serie VARCHAR(255) NOT NULL,
    anno_serie VARCHAR(4),
    titolo VARCHAR(255) NOT NULL,
    genere VARCHAR(100),
    casa_editrice VARCHAR(255),
    immagine_copertina LONGTEXT,
    descrizione TEXT,
    prezzo DECIMAL(10, 2),
    quantita_disponibile INT
);
