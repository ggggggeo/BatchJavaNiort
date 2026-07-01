package com.example.projetadresse2emeessai.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "adresse_table_brut")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdresseSansFiltrageEntityPourTableBrut {

    @Id
    @Column(name = "id_technique")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long techniqueId ;


    @Column(name = "id_adresse")
    private String adresseId;

    @Column(name = "id_fantoir")
    private String id_fantoir;

    @Column(name = "numero")
    private int numero;

    @Column(name = "rep")
    private String rep;

    @Column(name = "nom_voie")
    private String nom_voie;

    @Column(name = "code_postal")
    private String code_postal;

    @Column(name = "code_insee")
    private int code_insee;

    @Column(name = "nom_commune")
    private String nom_commune;

    @Column(name = "code_insee_ancienne_commune")
    private String code_insee_ancienne_commune;

    @Column(name = "nom_ancienne_commune")
    private String nom_ancienne_commune;

    @Column(name = "x")
    private BigDecimal x;

    @Column(name = "y")
    private BigDecimal y;

    @Column(name = "lon")
    private BigDecimal lon;

    @Column(name = "lat")
    private BigDecimal lat;

    @Column(name = "type_position")
    private String type_position;

    @Column(name = "alias")
    private String alias;

    @Column(name = "nom_ld")
    private String nom_ld;

    @Column(name = "libelle_acheminement")
    private String libelle_acheminement;

    @Column(name = "nom_afnor")
    private String nom_afnor;

    @Column(name = "source_position")
    private String source_position;

    @Column(name = "source_nom_voie")
    private String source_nom_voie;

    @Column(name = "certification_commune")
    private int certification_commune;

    @Column(name = "cad_parcelles")
    private String cad_parcelles;
}