package com.example.projetadresse2emeessai.batch;


import com.example.projetadresse2emeessai.dto.AdresseDto;
import com.example.projetadresse2emeessai.model.AdresseEntity;

import com.example.projetadresse2emeessai.repository.AdresseRepository;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;



@Component
public class AdresseProcessor implements ItemProcessor<AdresseDto, AdresseEntity> {

    private static final Pattern ID_PATTERN = Pattern.compile("^\\d{5}_.*");
    private final AdresseRepository adresseRepository;
    private final AdresseDoublonsWriter adresseDoublonsWriter;


    public AdresseProcessor(AdresseRepository adresseRepository,
                            AdresseDoublonsWriter adresseDoublonsWriter) {
        this.adresseRepository = adresseRepository;
        this.adresseDoublonsWriter = adresseDoublonsWriter;
    }




    @Override
    public AdresseEntity process(AdresseDto tx) {



        if (tx.code_postal() == null
                || tx.code_postal().length() != 5
                || !tx.code_postal().startsWith("79")) {
            throw new IllegalArgumentException("Adresse invalide: code postal invalide");

        }

        if (tx.code_insee() == 0){
            throw new IllegalArgumentException("Adresse invalide : code INSEE invalide");
        }

        AdresseEntity entity = new AdresseEntity();
        entity.setId(tx.id());
        entity.setId_fantoir(tx.id_fantoir());
        entity.setNumero(tx.numero());
        entity.setRep(tx.rep());
        entity.setNom_voie(tx.nom_voie());
        entity.setCode_postal(tx.code_postal());
        entity.setCode_insee(tx.code_insee());
        entity.setNom_commune(tx.nom_commune());
        entity.setCode_insee_ancienne_commune(tx.code_insee_ancienne_commune());
        entity.setNom_ancienne_commune(tx.nom_ancienne_commune());
        entity.setX(tx.x());
        entity.setY(tx.y());
        entity.setLon(tx.lon());
        entity.setLat(tx.lat());
        entity.setType_position(tx.type_position());
        entity.setAlias(tx.alias());
        entity.setNom_ld(tx.nom_ld());
        entity.setLibelle_acheminement(tx.libelle_acheminement());
        entity.setNom_afnor(tx.nom_afnor());
        entity.setSource_position(tx.source_position());
        entity.setSource_nom_voie(tx.source_nom_voie());
        entity.setCertification_commune(tx.certification_commune());
        entity.setCad_parcelles(tx.cad_parcelles());



        if (adresseRepository.existsById(entity.getId())) {
            adresseDoublonsWriter.writeDuplicate(entity);
        }





        return entity;
    }

}
