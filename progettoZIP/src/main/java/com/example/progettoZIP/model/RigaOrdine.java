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
@Table(name = "RigaOrdine")
public class RigaOrdine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRigaOrdine;
    private Integer quantitaProdotto;
    private Double prezzoUnitario;
    @ManyToOne
    @JoinColumn(name = "ordine_id_ordine")
    private Ordine ordine;
    @OneToOne
    @JoinColumn(name = "fumetto_id_fumetto")
    private Fumetto fumetto;
}
