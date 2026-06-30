package com.example.projetadresse2emeessai.dto;

import com.example.projetadresse2emeessai.model.AdresseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record AdresseDto(
//        @Pattern(regexp = "^\\d{5}_.*")
//        @NotBlank
          String id ,
          String id_fantoir ,
          int numero ,
          String rep ,
          String nom_voie ,
          String code_postal ,
          int code_insee ,
          String nom_commune ,
          String code_insee_ancienne_commune ,
          String nom_ancienne_commune ,
          BigDecimal x ,
          BigDecimal y ,
          BigDecimal lon ,
          BigDecimal lat ,
          String type_position ,
          String alias ,
          String nom_ld ,
          String libelle_acheminement ,
          String nom_afnor ,
          String source_position ,
          String source_nom_voie ,
          int certification_commune ,
          String cad_parcelles
) {


}


