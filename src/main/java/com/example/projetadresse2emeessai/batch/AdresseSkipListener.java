package com.example.projetadresse2emeessai.batch;

import com.example.projetadresse2emeessai.dto.AdresseDto;
import com.example.projetadresse2emeessai.model.AdresseEntity;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.SkipListener;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
@StepScope
public class AdresseSkipListener implements SkipListener<AdresseDto, AdresseEntity>, StepExecutionListener {
    private final List<AdresseDto> invalidTransactions = new ArrayList<>();
    private final String errorFile;

    public AdresseSkipListener(
            @Value("#{jobParameters['errorFile']}") String errorFile) {
        this.errorFile = errorFile;
    }

    @Override
    public void onSkipInProcess(AdresseDto item, Throwable t) {
        invalidTransactions.add(item);
    }

    // Écrire le fichier d'erreurs après le step (via StepExecutionListener)
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        long nombreDeLignesLues = stepExecution.getReadCount();
        stepExecution.getJobExecution()
                .getExecutionContext()
                .putLong("nombreDeLignesLues", nombreDeLignesLues);



        System.out.println("##Nombre de lignes lues : " + nombreDeLignesLues);

        return stepExecution.getExitStatus();


    }

}