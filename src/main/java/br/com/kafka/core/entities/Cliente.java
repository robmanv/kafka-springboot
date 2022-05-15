package br.com.kafka.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    private Integer id;   //id
    private String name; //nome
    private Integer age; //idade
    private Double height; //altura
    private Double weight; //peso
    private Double salary; //salario
    private String address; // endereço
}
