package br.com.kafka.adapters.out;

import br.com.kafka.core.entities.Cliente;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

public class CustomFlatFileItemReader extends FlatFileItemReader<Cliente> {

    public CustomFlatFileItemReader(Resource resource) {
        setResource(resource);
        setLinesToSkip(1);
        setLineMapper(lineMapper());
    }

    private DefaultLineMapper<Cliente> lineMapper() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setNames("id", "name", "age", "height", "weight", "salary", "address");

        BeanWrapperFieldSetMapper<Cliente> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Cliente.class);

        DefaultLineMapper<Cliente> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

}
