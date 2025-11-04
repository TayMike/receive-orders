package com.fase4.fiap.cache;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.net.ServerSocket;

@TestConfiguration
public class TestRedisConfiguration {

    private final RedisServer redisServer;

    public TestRedisConfiguration(@Value("${spring.data.redis.port:0}") int redisPort) throws IOException {
        int port = redisPort == 0 ? findFreePort() : redisPort;
        this.redisServer = RedisServer.newRedisServer()
                .port(port)
                .setting("bind 127.0.0.1")
                .setting("maxmemory 128M")
                .build();
        this.redisServer.start();
    }

    @Bean
    public RedisServer redisServer() {
        return redisServer;
    }

    @PreDestroy
    public void stopRedis() throws IOException {
        if (redisServer != null && redisServer.isActive()) {
            redisServer.stop();
        }
    }

    private int findFreePort() throws IOException {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }
}