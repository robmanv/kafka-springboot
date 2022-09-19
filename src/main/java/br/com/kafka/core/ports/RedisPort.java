package br.com.kafka.core.ports;

import br.com.kafka.core.entities.Cliente;

import java.util.concurrent.ExecutionException;

public interface RedisPort {
    public void upsertCacheEntry(String key, String value, boolean checkExists)
            throws InterruptedException, ExecutionException;
    public String getCacheValue(String key);
}
