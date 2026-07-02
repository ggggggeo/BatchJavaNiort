package com.example.projetadresse2emeessai.config;


import com.example.projetadresse2emeessai.batch.AdresseBrut.AdresseBrutProcessor;
import com.example.projetadresse2emeessai.dto.AdresseDto;
import com.example.projetadresse2emeessai.model.AdresseSansFiltrageEntity;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class AdresseBrutBatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager txManager;




    @Bean
    public Step importBrutStep(
            FlatFileItemReader<AdresseDto> reader,
            AdresseBrutProcessor adresseBrutProcessor,
            JpaItemWriter<AdresseSansFiltrageEntity> jpaBrutWriter) {

        return new StepBuilder("importBrutStep", jobRepository)
                .<AdresseDto, AdresseSansFiltrageEntity>chunk(1000)
                .transactionManager(txManager)
                .reader(reader)
                .processor(adresseBrutProcessor)
                .writer(jpaBrutWriter)
                .build();
    }

    @Bean
    public JpaItemWriter<AdresseSansFiltrageEntity> jpaBrutWriter(EntityManagerFactory emf) {
        return new JpaItemWriter<>(emf);
    }



    @Bean
    public Step cleanBrutTableStep(JdbcTemplate jdbcTemplate) {
        return new StepBuilder("cleanBrutTableStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    jdbcTemplate.execute("DELETE FROM adresse_table_brut");
                    jdbcTemplate.execute("DELETE FROM sqlite_sequence WHERE name = 'adresse_table_brut'");
                    return RepeatStatus.FINISHED;
                }, txManager)
                .build();
    }


}
