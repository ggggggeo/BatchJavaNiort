package com.example.projetadresse2emeessai.batch;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.stereotype.Component;
//
//@Component
//public class CustomBatchMetrics implements StepExecutionListener {
//
//    private final MeterRegistry meterRegistry;
//    private final Counter processedCounter;
//
//    public CustomBatchMetrics(MeterRegistry meterRegistry) {
//        this.meterRegistry  = meterRegistry;
//        this.processedCounter = Counter.builder("batch.employee.processed")
//                .description("Nombre d'employés traités")
//                .register(meterRegistry);
//    }
