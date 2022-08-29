package br.com.kafka.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
