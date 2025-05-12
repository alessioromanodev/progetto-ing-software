package com.example.progettoZIP.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Fumetto")
public class Fumetto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFumetto;
    private String nomeSerie;
    private Integer anno;
    private Integer numeroVolume;
    private String titolo;
    private String descrizione;
    private String genere;
    private String casaEditrice;
    private String immagineCopertina;
    private Double prezzo;
    private Integer quantita;
}
