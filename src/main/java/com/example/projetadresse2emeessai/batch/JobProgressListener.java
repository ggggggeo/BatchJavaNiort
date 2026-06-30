package com.example.projetadresse2emeessai.batch;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.stereotype.Component;

import java.time.Duration;


@Component
public class JobProgressListener  implements JobExecutionListener {
    private static final Logger log =
            LoggerFactory.getLogger(JobProgressListener.class);

    public void beforeJob(JobExecution jobExecution) {
        log.info("Demarrage du job [{}] avec les parametres : {}",
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getJobParameters());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("Job [{}] termine avec le statut : {} en {} ms",
                jobExecution.getJobInstance().getJobName(),
                jobExecution.getStatus(),
                Duration.between(
                        jobExecution.getStartTime(),
                        jobExecution.getEndTime()).toMillis());
    }

}