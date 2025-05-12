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
@Table(name = "Utente")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtente;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private String telefono;
}
