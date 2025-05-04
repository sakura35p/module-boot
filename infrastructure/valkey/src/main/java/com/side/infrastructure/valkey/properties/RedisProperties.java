package com.side.infrastructure.valkey.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties("redis")
public class RedisProperties {

    private Cluster cluster = new Cluster();
    private String password;
    private long timeout;
    private int maxRedirects;

    // Cluster 설정용 내부 클래스 추가
    @Getter
    @Setter
    public static class Cluster {
        private Set<String> nodes;  // "host:port" 형식의 노드 목록 (예: "valkey-1:6379")
    }
}