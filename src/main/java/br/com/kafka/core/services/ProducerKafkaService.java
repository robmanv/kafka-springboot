package br.com.kafka.core.services;

import br.com.kafka.core.entities.Cliente;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ProducerKafkaService {

    public GenericRecord gerarSchema(Cliente cliente) throws IOException {
        //Instantiating the Schema.Parser class.
        Schema schema = new Schema.Parser().parse(new File("/Users/robsvel/projetos/kafka-springboot/src/main/resources/avro/emp.avsc"));


        //Instantiating the GenericRecord class.
        GenericRecord record = new GenericData.Record(schema);

        //Insert data according to schema
        record.put("name", cliente.getName());
        record.put("id", cliente.getId());
        record.put("salary", cliente.getSalary());
        record.put("age", cliente.getAge());
        record.put("height", cliente.getHeight());
        record.put("weight", cliente.getWeight());
        record.put("address", cliente.getAddress());

        return record;

    }
}
