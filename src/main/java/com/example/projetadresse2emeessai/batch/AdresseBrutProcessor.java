package com.example.projetadresse2emeessai.batch;

import com.example.projetadresse2emeessai.dto.AdresseDto;
import com.example.projetadresse2emeessai.model.AdresseSansFiltrageEntityPourTableBrut;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class AdresseBrutProcessor implements ItemProcessor<AdresseDto, AdresseSansFiltrageEntityPourTableBrut> {

    @Override
    public AdresseSansFiltrageEntityPourTableBrut process(AdresseDto tx) {
        return AdresseSansFiltrageEntityPourTableBrut.builder()
                .adresseId(tx.id())
                .id_fantoir(tx.id_fantoir())
                .numero(tx.numero())
                .rep(tx.rep())
                .nom_voie(tx.nom_voie())
                .code_postal(tx.code_postal())
                .code_insee(tx.code_insee())
                .nom_commune(tx.nom_commune())
                .code_insee_ancienne_commune(tx.code_insee_ancienne_commune())
                .nom_ancienne_commune(tx.nom_ancienne_commune())
                .x(tx.x())
                .y(tx.y())
                .lon(tx.lon())
                .lat(tx.lat())
                .type_position(tx.type_position())
                .alias(tx.alias())
                .nom_ld(tx.nom_ld())
                .libelle_acheminement(tx.libelle_acheminement())
                .nom_afnor(tx.nom_afnor())
                .source_position(tx.source_position())
                .source_nom_voie(tx.source_nom_voie())
                .certification_commune(tx.certification_commune())
                .cad_parcelles(tx.cad_parcelles())
                .build();
    }
}
