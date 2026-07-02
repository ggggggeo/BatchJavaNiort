package com.example.projetadresse2emeessai.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "adresse_rejete_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdresseReject {
    @Id
    @Column(name = "id_technique")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long techniqueId ;
}








//
//
//
//
//@Entity
//@Table(name = "adresse_table_brut")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class AdresseSansFiltrageEntity {
//
//    @Id
//    @Column(name = "id_technique")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long techniqueId ;
//
//
//    @Column(name = "id_adresse")
//    private String adresseId;
//
//    @Column(name = "id_fantoir")
//    private String id_fantoir;
//
//    @Column(name = "numero")
//    private int numero;
//
//    @Column(name = "rep")
//    private String rep;
//
//    @Column(name = "nom_voie")
//    private String nom_voie;
//
//    @Column(name = "code_postal")
//    private String code_postal;
//
//    @Column(name = "code_insee")
//    private int code_insee;
//
//    @Column(name = "nom_commune")
//    private String nom_commune;
//