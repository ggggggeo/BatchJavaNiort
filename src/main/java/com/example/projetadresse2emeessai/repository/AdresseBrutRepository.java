package com.example.projetadresse2emeessai.repository;

import com.example.projetadresse2emeessai.model.AdresseSansFiltrageEntityPourTableBrut;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdresseBrutRepository
        extends JpaRepository<AdresseSansFiltrageEntityPourTableBrut, Long> {
}
