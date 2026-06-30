package com.example.projetadresse2emeessai.repository;

import com.example.projetadresse2emeessai.model.AdresseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdresseRepository extends JpaRepository<AdresseEntity,String>, JpaSpecificationExecutor<AdresseEntity> {


}
