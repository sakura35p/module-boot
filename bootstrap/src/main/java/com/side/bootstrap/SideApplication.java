package com.side.bootstrap;

import com.side.infrastructure.datasource.DataSourceProperties;
import com.side.infrastructure.valkey.properties.RedisProperties;
import com.side.security.jwt.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

@ConfigurationPropertiesScan(basePackageClasses = {JwtProperties.class, RedisProperties.class, DataSourceProperties.class})
@SpringBootApplication(scanBasePackages = {
        "com.side.security.config",
        "com.side.security.service",
        "com.side.security.jwt.config",
        "com.side.security.jwt.filter",
        "com.side.security.jwt.service",
        "com.side.rest.advice",
        "com.side.rest.controller",
        "com.side.rest.usecase",
        "com.side.websocket.config",
        "com.side.websocket.controller",
        "com.side.domain.service",
        "com.side.domain.memory",
        "com.side.infrastructure.datasource",
        "com.side.infrastructure.jooq.config",
        "com.side.infrastructure.jooq.repository",
        "com.side.infrastructure.jpa.config",
        "com.side.infrastructure.jpa.repository",
        "com.side.infrastructure.valkey.cache.config",
        "com.side.infrastructure.valkey.cache.service",
        "com.side.infrastructure.valkey.config",
        "com.side.infrastructure.valkey.properties",
        "com.side.infrastructure.valkey.repository",
})
public class SideApplication {

    public static void main(String[] args) {
        SpringApplication.run(SideApplication.class, args);
    }
}