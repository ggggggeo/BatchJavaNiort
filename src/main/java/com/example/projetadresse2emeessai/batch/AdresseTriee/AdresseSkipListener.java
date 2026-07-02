package com.example.projetadresse2emeessai.batch.AdresseTriee;

import com.example.projetadresse2emeessai.dto.AdresseDto;
import com.example.projetadresse2emeessai.model.AdresseEntity;
import com.example.projetadresse2emeessai.repository.AdresseRepository;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.SkipListener;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@StepScope

public class AdresseSkipListener implements SkipListener<AdresseDto, AdresseEntity>, StepExecutionListener {
    private final List<AdresseDto> invalidTransactions = new ArrayList<>();
    private final String errorFile;
    private final AdresseRepository adresseRepository;

    public AdresseSkipListener(
            @Value("#{jobParameters['errorFile']}") String errorFile,
            AdresseRepository adresseRepository
            ) {
        this.errorFile = errorFile;
        this.adresseRepository = adresseRepository;
    }

    @Override
    public void onSkipInProcess(AdresseDto item, Throwable t) {
        invalidTransactions.add(item);
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        long nombreLigneBaseAvantImport = adresseRepository.count();

        stepExecution.getJobExecution()
                .getExecutionContext()
                .putLong("nombreLigneBaseAvantImport", nombreLigneBaseAvantImport);


    }




    // Écrire le fichier d'erreurs après le step (via StepExecutionListener)
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        long nombreDeLignesLues = stepExecution.getReadCount();
        long nombreDeLignesEcrites = stepExecution.getWriteCount();
        long nombreLigneBaseApresImport = adresseRepository.count();

        stepExecution.getJobExecution()
                .getExecutionContext()
                .putLong("nombreLigneBaseApresImport", nombreLigneBaseApresImport);

        stepExecution.getJobExecution()
                .getExecutionContext()
                .putLong("nombreDeLignesLues", nombreDeLignesLues);

        stepExecution.getJobExecution()
                .getExecutionContext()
                .putLong("nombreDeLignesEcrites", nombreDeLignesEcrites);


        System.out.println("##Nombre de lignes lues : " + nombreDeLignesLues);

        return stepExecution.getExitStatus();


    }

}