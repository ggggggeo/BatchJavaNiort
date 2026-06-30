package com.example.projetadresse2emeessai.controller;

import com.example.projetadresse2emeessai.dto.AdresseSearchRequest;
import com.example.projetadresse2emeessai.model.AdresseEntity;
import com.example.projetadresse2emeessai.service.AdresseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/adresses")
@RequiredArgsConstructor
public class AdresseController {

    private final AdresseService adresseService;


    @GetMapping
    public List<AdresseEntity> findAll(
    ) {
        return adresseService.findAll();
    }


    @GetMapping("/recherchePage")
    public Page<AdresseEntity> recherchePage(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)  Pageable pageable) {
        return adresseService.findAllPage(pageable);
    }


    @GetMapping("/recherchePageAvecFiltre")
    public Page<AdresseEntity> recherchePageFiltre(
            @RequestParam(required = false) String codePostal,
            @RequestParam(required = false) String rue,
            @RequestParam(required = false) String commune,
            @PageableDefault(size = 20, sort = "code_postal", direction = Sort.Direction.ASC) Pageable pageable
    ){
        return adresseService.recherchePageFiltre(
                new AdresseSearchRequest(codePostal,rue,commune),
                pageable);
    }





}
