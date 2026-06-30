package com.example.projetadresse2emeessai.specification;

import com.example.projetadresse2emeessai.dto.AdresseSearchRequest;
import com.example.projetadresse2emeessai.model.AdresseEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
import java.util.stream.Stream;

public class AdresseSpecification {
    public static Specification<AdresseEntity> build(AdresseSearchRequest criteria) {
        return Specification.allOf(
                Stream.of(
                                codePostalContient(criteria.codePostal()),
                                rueContient(criteria.rue()),
                                communeContient(criteria.commune())
                        )
                        .filter(Objects::nonNull)
                        .toList()
        );
    }
    private static Specification<AdresseEntity> codePostalContient(String codePostal) {
        if (codePostal == null || codePostal.isBlank()) return null;
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("code_postal")), "%" + codePostal.toLowerCase() + "%");
    }

    private static Specification<AdresseEntity> rueContient(String rue) {
        if (rue == null || rue.isBlank()) return null;
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("nom_voie")), "%" + rue.toLowerCase() + "%");
    }

    private static Specification<AdresseEntity> communeContient(String commune) {
        if (commune == null || commune.isBlank()) return null;
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("nom_commune")), "%" + commune.toLowerCase() + "%");
    }


    //codePostal,rue,commune





}
