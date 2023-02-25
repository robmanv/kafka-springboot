package br.com.kafka.adapters.out;

import br.com.kafka.core.entities.Cliente;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.batch.runtime.StepExecution;
import javax.batch.runtime.context.StepContext;
import java.util.Iterator;
import java.util.List;

public class CustomFlatFileItemWriter extends FlatFileItemWriter<Cliente> {
    @Autowired
    ExecutionContext executionContext;

    private int updateCount = 0;

    public CustomFlatFileItemWriter(Resource resource) {
        setResource(new FileSystemResource("clientes.txt"));
        setAppendAllowed(true);
        setEncoding("UTF-8");
        setLineAggregator(new DelimitedLineAggregator<>() {{
            setDelimiter(";");
            setFieldExtractor(new BeanWrapperFieldExtractor<>(){{
                setNames(new String[]{"id", "name", "age", "height", "weight", "salary", "address"});
            }});
        }});
    }

    public String doWrite(List<? extends Cliente> items) {
        StringBuilder lines = new StringBuilder();
        Iterator var3 = items.iterator();

        while(var3.hasNext()) {
            Object item = var3.next();
            lines.append(this.lineAggregator.aggregate((Cliente) item)).append(this.lineSeparator);
            updateCount++;
        }

        return lines.toString();
    }

    public int getUpdateCount() {
        return updateCount;
    }

    @AfterStep
    public void afterStep() {
        System.out.println("UPDATE COUNT: " + updateCount);
    }

}


