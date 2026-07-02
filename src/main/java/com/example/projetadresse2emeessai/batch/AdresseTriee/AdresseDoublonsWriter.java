package com.example.projetadresse2emeessai.batch.AdresseTriee;


import com.example.projetadresse2emeessai.model.AdresseDoublonEntity;
import com.example.projetadresse2emeessai.model.AdresseEntity;
import com.example.projetadresse2emeessai.repository.AdresseDoublonRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class AdresseDoublonsWriter implements StepExecutionListener {

    private final AdresseDoublonRepository adresseDoublonRepository;



    public AdresseDoublonsWriter(AdresseDoublonRepository adresseDoublonRepository) {
        this.adresseDoublonRepository = adresseDoublonRepository;
    }

//    @Override
//    public void beforeStep(StepExecution stepExecution) {
//        try {
//            writer = new PrintWriter("duplicates.csv");
//            writer.println("id;code_postal;nom_commune;nom_voie;numero");
//        } catch (IOException e) {
//            throw new RuntimeException("Impossible de creer le fichier duplicates.csv", e);
//        }
//    }

    public void writeDuplicate(AdresseEntity entity) {
        AdresseDoublonEntity doublon = AdresseDoublonEntity.builder()
                .adresseId(entity.getId())
                .codePostal(entity.getCode_postal())
                .nomCommune(entity.getNom_commune())
                .nomVoie(entity.getNom_voie())
                .numero(entity.getNumero())
                .build();

        adresseDoublonRepository.save(doublon);
    }

//    @Override
//    public ExitStatus afterStep(StepExecution stepExecution) {
//        if (writer != null) {
//            writer.close();
//        }
//        return stepExecution.getExitStatus();
//    }
}