package br.com.kafka.adapters.out;

import br.com.kafka.core.entities.Cliente;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

public class CustomFlatFileItemWriter extends FlatFileItemWriter<Cliente> {
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
}


