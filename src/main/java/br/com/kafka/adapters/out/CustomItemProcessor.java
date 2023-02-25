package br.com.kafka.adapters.out;

import br.com.kafka.core.entities.Cliente;
import com.google.common.util.concurrent.AtomicDouble;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CustomItemProcessor implements ItemProcessor<Cliente, Cliente> {
    @Autowired
    ExecutionContext executionContext;

    private int updateCount = 0;
    private AtomicDouble qtdProcessados;
    private ConcurrentHashMap<String, AtomicDouble> qtdMap;

    public CustomItemProcessor() {
        qtdProcessados = new AtomicDouble(0.0);
        qtdMap = new ConcurrentHashMap<>();
    }


    @Override
    public Cliente process(Cliente cliente) throws Exception {
//        updateCount++;
        qtdProcessados.addAndGet(1.0);
        qtdMap.put("INSERIDOS", qtdProcessados);
        return cliente;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    @AfterProcess
    public void afterProcess(Cliente inputCliente, Cliente outputCliente) {
        updateCount++;
    }

    @AfterStep
    public void afterStep() {
        System.out.println("UPDATE COUNT DO ITEM PROCESSOR: " + updateCount);
    }

}
