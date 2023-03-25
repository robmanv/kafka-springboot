package br.com.kafka.adapters.out;

import br.com.kafka.core.entities.Cliente;
import com.google.common.util.concurrent.AtomicDouble;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;

import java.util.concurrent.ConcurrentHashMap;

public class CustomItemProcessorListener implements ItemProcessListener<Cliente, Cliente> {

    private int processedCount = 0;

    @Override
    public void beforeProcess(Cliente item) {
        // Lógica que deve ser executada antes do processamento do item
    }

    @Override
    public void afterProcess(Cliente item, Cliente result) {
        // Lógica que deve ser executada após o processamento do item

        // Atualiza a contagem de itens processados
        processedCount++;
    }

    @Override
    public void onProcessError(Cliente item, Exception e) {
        // Lógica que deve ser executada se ocorrer um erro no processamento do item
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution) {
        System.out.println("]");
    }

    public int getProcessedCount() {
        return processedCount;
    }
}