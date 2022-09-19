package br.com.kafka.adapters.out;

import br.com.kafka.core.ports.RedisPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.params.SetParams;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@Service
public class RedisAdapter implements RedisPort {

    @Autowired
    Jedis jedis;

    @Value("${redis.aws.cache-expiracy}")
    private Integer cacheExpiryInSecs;

    public void upsertCacheEntry(String key, String value, boolean checkExists)
            throws InterruptedException, ExecutionException {
        boolean valueExists = false;
        if (checkExists && (getCacheValue(key) != null)) {
            valueExists = true;
        }
        String result = jedis.set(key, value, (new SetParams()).ex(cacheExpiryInSecs));
        if (result.equalsIgnoreCase("OK")) {
            if (checkExists) {
                if (valueExists) {
                    System.out.println("Updated = {key=" + key + ", value=" + value + "}");
                } else {
                    System.out.println("Inserted = {key=" + key + ", value=" + value + "}");
                }
            } else {
                System.out.println("Upserted = {key=" + key + ", value=" + value + "}");
            }
        } else {
            System.out.println("Could not upsert key '" + key + "'");
        }
    }

    public String getCacheValue(String key) {
        long startTime = (new Date()).getTime();
        String value = jedis.get(key);
        long endTime = (new Date()).getTime();
        if (value != null) {
            System.out.println("Retrieved value='" + value + "' for key= '" + key + "' in " + (endTime - startTime)
                    + " millisecond(s).");
        } else {
            System.out.println("Key '" + key + "' not found.");
        }
        return value;
    }

}
