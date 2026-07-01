package com.example.projetadresse2emeessai.config;
/*Patrick1*/

import com.example.projetadresse2emeessai.batch.AdresseProcessor;
import com.example.projetadresse2emeessai.batch.AdresseSkipListener;
import com.example.projetadresse2emeessai.batch.JobProgressListener;
import com.example.projetadresse2emeessai.dto.AdresseDto;
import com.example.projetadresse2emeessai.model.AdresseEntity;
import com.example.projetadresse2emeessai.model.AdresseSansFiltrageEntityPourTableBrut;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.SkipListener;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.support.CompositeItemProcessor;
import org.springframework.batch.infrastructure.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import org.springframework.transaction.PlatformTransactionManager;
import com.example.projetadresse2emeessai.batch.AdresseBrutProcessor;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AdresseBatchConfig {
    private static final Logger log =
            LoggerFactory.getLogger(JobProgressListener.class);


    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;

    @Bean
    public Job AdresseDtoImportJob(Step importBrutStep,
                                   Step validateAndImportStep,
                                    Step reportStep,
                                    JobProgressListener listener) {
        return new JobBuilder("AdresseDtoImportJob", jobRepository)
                .listener(listener)
                .start(importBrutStep)
                .next(validateAndImportStep)
                .next(reportStep)
                .build();
    }

    @Bean
    public Step importBrutStep(
            FlatFileItemReader<AdresseDto> reader,
            AdresseBrutProcessor adresseBrutProcessor,
            JpaItemWriter<AdresseSansFiltrageEntityPourTableBrut> jpaBrutWriter) {

        return new StepBuilder("importBrutStep", jobRepository)
                .<AdresseDto, AdresseSansFiltrageEntityPourTableBrut>chunk(1000)
                .transactionManager(txManager)
                .reader(reader)
                .processor(adresseBrutProcessor)
                .writer(jpaBrutWriter)
                .build();
    }




    @Bean
    public Step validateAndImportStep(
            FlatFileItemReader<AdresseDto> reader,
            CompositeItemProcessor<AdresseDto, AdresseEntity> processor,
            JpaItemWriter<AdresseEntity> jpaWriter,
            AdresseSkipListener skipListener) {
        return new StepBuilder("validateAndImportStep", jobRepository)
                .<AdresseDto, AdresseEntity>chunk(1000)
                .transactionManager(txManager)
                .reader(reader)
                .processor(processor)
                .writer(jpaWriter)
                .faultTolerant()
                .skipLimit(100)
                .skip(Exception.class)
                .listener((SkipListener<AdresseDto,AdresseEntity>)
                        skipListener)
                .listener((StepExecutionListener) skipListener)
                .build();
    }

    @Bean
    public Step reportStep() {
        return new StepBuilder("reportStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    ExecutionContext ctx = chunkContext.getStepContext()
                            .getStepExecution().getJobExecution().getExecutionContext();
                    long total   = ctx.getLong("nombreDeLignesLues",   0L);
                    long nbLigneEcriteParWriter   = ctx.getLong("nombreDeLignesEcrites",   0L);
                    long nbLigneInsereeEnBase = ctx.getLong("nombreLigneBaseApresImport",0L) -
                            ctx.getLong("nombreLigneBaseAvantImport",0L);



                    //stepExecution.getReadCount()
                    long skipped = chunkContext.getStepContext()
                            .getStepExecution().getJobExecution()
                            .getStepExecutions().stream()
                            .mapToLong(StepExecution::getSkipCount).sum();

                    log.info("=== RAPPORT FINAL ===");
                    log.info("Total traité : {} ", total);
                    log.info("Nombre de ligne écrite par le writer : {} ", nbLigneEcriteParWriter);
                    log.info("Nombre de ligne écrite dans la base : {} ", nbLigneInsereeEnBase);
                    log.info("Adresses invalides : {}", skipped);
                    log.info("Notification envoyée.");

                    return RepeatStatus.FINISHED;
                }, txManager)
                .build();
    }


    @Bean
    @StepScope
    public FlatFileItemReader<AdresseDto> AdresseDtoReader(
            @Value("${batch.input.file:}") String inputFile) {
        //Le @Value récupère ce qu'il y a dans application.properties
        //@Value("${batch.address.departement:}") String department,

        return new FlatFileItemReaderBuilder<AdresseDto>()
                .name("AdresseReader")
                .resource(new FileSystemResource(inputFile))
                .delimited()
                .delimiter(";")
                .names(
                        "id",
                        "id_fantoir",
                        "numero",
                        "rep",
                        "nom_voie",
                        "code_postal",
                        "code_insee",
                        "nom_commune",
                        "code_insee_ancienne_commune",
                        "nom_ancienne_commune",
                        "x",
                        "y",
                        "lon",
                        "lat",
                        "type_position",
                        "alias",
                        "nom_ld",
                        "libelle_acheminement",
                        "nom_afnor",
                        "source_position",
                        "source_nom_voie",
                        "certification_commune",
                        "cad_parcelles"
                )
                .fieldSetMapper(fs -> new AdresseDto(
                        fs.readString("id"),
                        fs.readString("id_fantoir"),
                        fs.readInt("numero"),
                        fs.readString("rep"),
                        fs.readString("nom_voie"),
                        fs.readString("code_postal"),
                        fs.readInt("code_insee"),
                        fs.readString("nom_commune"),
                        fs.readString("code_insee_ancienne_commune"),
                        fs.readString("nom_ancienne_commune"),
                        fs.readBigDecimal("x"),
                        fs.readBigDecimal("y"),
                        fs.readBigDecimal("lon"),
                        fs.readBigDecimal("lat"),
                        fs.readString("type_position"),
                        fs.readString("alias"),
                        fs.readString("nom_ld"),
                        fs.readString("libelle_acheminement"),
                        fs.readString("nom_afnor"),
                        fs.readString("source_position"),
                        fs.readString("source_nom_voie"),
                        fs.readInt("certification_commune"),
                        fs.readString("cad_parcelles")
                ))

                .linesToSkip(1)
                .build();
    }

    @Bean
    public BeanValidatingItemProcessor<AdresseEntity> validatingAdresseEntityProcessor() throws Exception {
        BeanValidatingItemProcessor<AdresseEntity> processor = new BeanValidatingItemProcessor<>();
        //processor.setFilter(true);
        processor.afterPropertiesSet();
        return processor;
    }



    @Bean
    public CompositeItemProcessor<AdresseDto, AdresseEntity> compositeProcessor(
            BeanValidatingItemProcessor<AdresseEntity> validatingAdresseEntityProcessor,
            AdresseProcessor adresseProcessor
    ) {
        CompositeItemProcessor<AdresseDto, AdresseEntity> processor = new CompositeItemProcessor<>();
        processor.setDelegates(List.of(
                adresseProcessor,
                validatingAdresseEntityProcessor

        ));
        return processor;
    }




    @Bean
    public JpaItemWriter<AdresseSansFiltrageEntityPourTableBrut> jpaBrutWriter(EntityManagerFactory emf) {
        return new JpaItemWriter<>(emf);
    }



    @Bean
    public JpaItemWriter<AdresseEntity> jpaWriter(EntityManagerFactory emf) {
        return new JpaItemWriter<>(emf);
    }


}