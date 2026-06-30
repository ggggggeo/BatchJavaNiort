package com.example.projetadresse2emeessai.service;

import com.example.projetadresse2emeessai.dto.AdresseDto;
import com.example.projetadresse2emeessai.dto.AdresseSearchRequest;
import com.example.projetadresse2emeessai.model.AdresseEntity;
import com.example.projetadresse2emeessai.repository.AdresseRepository;

import com.example.projetadresse2emeessai.specification.AdresseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdresseService {
    private final AdresseRepository adresseRepository;

    @Transactional(readOnly = true)
    public List<AdresseEntity> findAll(){
        return adresseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<AdresseEntity> findAllPage(Pageable pageable) {
        return adresseRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<AdresseEntity> recherchePageFiltre (AdresseSearchRequest criteria, Pageable pageable){
        return adresseRepository.findAll(AdresseSpecification.build(criteria),pageable);
    }











}
