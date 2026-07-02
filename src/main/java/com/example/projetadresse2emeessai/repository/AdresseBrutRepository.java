package com.example.projetadresse2emeessai.repository;

import com.example.projetadresse2emeessai.model.AdresseSansFiltrageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdresseBrutRepository
        extends JpaRepository<AdresseSansFiltrageEntity, Long> {
}
