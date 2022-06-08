package br.com.kafka.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Post {

    @JsonProperty
    private Integer userId;

    @JsonProperty
    private Integer id;

    @JsonProperty
    private String title;

    @JsonProperty
    private String body;

}
