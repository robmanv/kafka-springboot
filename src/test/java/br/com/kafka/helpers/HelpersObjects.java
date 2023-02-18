package br.com.kafka.helpers;

import br.com.kafka.core.entities.Post;

public class HelpersObjects {
    public static Post getUsuarioUm() {
        return Post.builder()
                .userId(1)
                .id(1)
                .title("sunt aut facere repellat provident occaecati excepturi optio reprehenderit")
                .body("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto")
                .build();
    }
}
