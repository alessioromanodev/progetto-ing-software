package com.example.progettoZIP.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Consegna")
public class Consegna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConsegna;
    private LocalDateTime dataRichiesta;
    private LocalDateTime dataConsegna;
    private String Stato;
    @OneToOne
    @JoinColumn(name = "ordine_id_ordine")
    private Ordine ordine;
}
