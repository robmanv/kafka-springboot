package br.com.kafka.core.services;

import br.com.kafka.core.entities.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisService {

    @Autowired
    Jedis jedis;

    public void montarRedis() {
//        ListStructure<Cliente> shippingCart = RedisStrutureBuilder.ofList(jedis,
//                Cliente.class).withNameSpace("list_clientes").build();
//        List<Cliente> fruitsCarts =  shippingCart.get(FRUITS);
//        fruitsCarts.add(banana);
//        ProductCart banana = fruitsCarts.get(0);
//
//
//        User otaviojava = new User("otaviojava");
//        User felipe = new User("ffrancesquini");
//        SetStructure<User> socialMediaUsers = RedisStrutureBuilder.ofSet(RedisConnection.JEDIS,
//                User.class).withNameSpace("socialMedia").build();
//        Set<User> users = socialMediaUsers.createSet("twitter");
//        users.add(otaviojava);
//        users.add(otaviojava);
//        users.add(felipe);
//        users.add(otaviojava);
//        users.add(felipe);
////haverá apenas um objeto otaviojava e um felipe, já que ele impede a
//        duplicação da lista
////lembrando que os métodos de equals e hascode dos objetos não serão
//        utilizados, o que será considerado é a String do objeto gerado.
//
//
//        Species mammals = new Species("lion", "cow", "dog");
//        Species fishes = new Species("redfish", "glassfish");
//        Species amphibians = new Species("crododile", "frog");
//
//        MapStructure<Species> zoo = =
//        RedisStrutureBuilder.ofMap(RedisConnection.JEDIS, Species.class)
//                .withNameSpace("animalZoo").build();
//
//        Map<String, Species> vertebrates = zoo.get("vertebrates");
//        vertebrates.put("mammals", mammals);
//        vertebrates.put("mammals", mammals);
//        vertebrates.put("fishes", fishes);
//        vertebrates.put("amphibians", amphibians);
//
//        QueueStructure<LineBank> serviceBank =
//                RedisStrutureBuilder.ofQueue(RedisConnection.JEDIS,
//                        LineBank.class).withNameSpace("serviceBank").build();
//        QueueStructure<LineBank> serviceBank =
//                RedisStrutureBuilder.ofQueue(RedisConnection.JEDIS,
//                        LineBank.class).withNameSpace("serviceBank").build();
//
//        Queue<LineBank> lineBank = serviceBank.get("createAccount");
//        lineBank.add(new LineBank("Otavio", 25));
//        LineBank otavio = lineBank.poll();
    }
}
