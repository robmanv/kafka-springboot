package br.com.kafka.application;

import redis.clients.jedis.Jedis;

public class SSLTest
{
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 6379;

    public static void main (String[] args) {

        try {
            System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
            System.setProperty("javax.net.ssl.keyStore", "path/to/cert/redis_key_store.p12");
            System.setProperty("javax.net.ssl.keyStorePassword", "keystore_pwd");
            System.setProperty("javax.net.ssl.trustStoreType", "JKS");
            System.setProperty("javax.net.ssl.trustStore", "path/to/cert/truststore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "truststore_pwd");

            Jedis jedis = new Jedis(HOSTNAME, PORT, true);
            jedis.connect();
            jedis.auth("bTFpxILKJGuuXYVormvpgGtQiqsdIjvsLonPSOTxHdvsprWzRwhHtIYPnRIAHZgFRnZkNIrvJaJnpeFDkjUHRrXejlaUcDmreoGuBZqwndqajAuAZrlGQIqkxHaVIsAB");

            System.out.println(jedis.ping());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}