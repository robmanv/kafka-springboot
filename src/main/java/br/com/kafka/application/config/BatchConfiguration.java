package br.com.kafka.application.config;

import br.com.kafka.adapters.out.*;
import br.com.kafka.core.entities.Cliente;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.annotation.PostConstruct;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("classpath:arquivo.csv")
    private Resource inputFile;
    @Value("classpath:clientes.xlsx")
    private Resource inputFileXLS;

    @Value("classpath:clientes.txt")
    private Resource outputFile;

//    @Autowired
//    private JobLauncher jobLauncher;
//
//    @PostConstruct
//    public void startJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
//        JobParameters jobParameters = new JobParametersBuilder()
//                .addString("inputFile", "classpath:clientes.txt")
//                .toJobParameters();
//        jobLauncher.run(myJob(), jobParameters);
//    }

    @org.springframework.context.annotation.Bean
    public CustomFlatFileItemReader myFlatFileReader() {

        return new CustomFlatFileItemReader(inputFile);
    }

    @org.springframework.context.annotation.Bean
    public CustomItemProcessorListener customItemProcessorListener() {

        return new CustomItemProcessorListener();
    }

    @org.springframework.context.annotation.Bean
    public CustomItemProcessor customItemProcessor() {

        return new CustomItemProcessor();
    }

    @org.springframework.context.annotation.Bean
    public ExcelFlatFileItemReader excelFlatFileReader() {
        ExcelFlatFileItemReader excelFlatFileItemReader = new ExcelFlatFileItemReader();

        excelFlatFileItemReader.setName("excelFileReader");
        excelFlatFileItemReader.setResource(inputFileXLS);
        excelFlatFileItemReader.doOpen();

        return excelFlatFileItemReader;
    }

    @org.springframework.context.annotation.Bean
    public ItemWriter<Cliente> myObjectWriter() {
        // Configura um ItemWriter para gravar os objetos MyObject em algum lugar (por exemplo, em um banco de dados)
        return new CustomFlatFileItemWriter(outputFile);
    }

    @org.springframework.context.annotation.Bean
    public Step myStep() {
        return stepBuilderFactory.get("myStep")
                .<Cliente, Cliente>chunk(50)
//                .reader(myFlatFileReader())
                .reader(excelFlatFileReader())
                .processor(customItemProcessor())
                .writer(myObjectWriter())
                .listener(customItemProcessorListener())
                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    @org.springframework.context.annotation.Bean
    public Job myJob() {
        return jobBuilderFactory.get("myJob")
                .incrementer(new RunIdIncrementer())
                .flow(myStep())
                .end()
                .build();
    }

}
