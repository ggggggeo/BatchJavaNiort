package com.example.projetadresse2emeessai.dto;

public record AdresseSearchRequest(
        String codePostal,
        String rue,
        String commune)
{ }
