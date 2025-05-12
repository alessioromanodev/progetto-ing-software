package com.example.progettoZIP.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Ordine")
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrdine;
    private LocalDateTime dataOrdine;
    private Double importoTotale;
    private String metodoConsegna;
    private String statoOrdine;
    private String qrCode;
    @OneToMany(mappedBy = "ordine")
    private List<RigaOrdine> righeOrdini;
}
