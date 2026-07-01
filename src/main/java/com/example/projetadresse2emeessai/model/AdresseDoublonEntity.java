package com.example.projetadresse2emeessai.model;

import jakarta.persistence.*;
import lombok.*;




@Entity
@Table(name = "Adresse_doublons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdresseDoublonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adresseId;
    private String codePostal;
    private String nomCommune;
    private String nomVoie;
    private int numero;
}