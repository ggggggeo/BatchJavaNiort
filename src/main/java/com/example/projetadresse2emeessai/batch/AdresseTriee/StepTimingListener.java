package com.example.projetadresse2emeessai.batch.AdresseTriee;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.stereotype.Component;

// StepExecutionListener
@Component
public class StepTimingListener implements StepExecutionListener {

    private static final Logger log =
            LoggerFactory.getLogger(StepTimingListener.class);


    @Override
    public void beforeStep(StepExecution stepExecution) {
        stepExecution.getExecutionContext().put("startNano", System.nanoTime());
    }



    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        long elapsed = System.nanoTime() -
                (long) stepExecution.getExecutionContext().get("startNano");
        log.info("Step [{}] : {} éléments lus, {} écrits, {} ignorés — {}ms",
                stepExecution.getStepName(),
                stepExecution.getReadCount(),
                stepExecution.getWriteCount(),
                stepExecution.getSkipCount(),
                elapsed / 1_000_000);
        return stepExecution.getExitStatus();
    }
}