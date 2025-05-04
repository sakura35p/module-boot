package com.side.security.jwt.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("security.jwt")
public class JwtProperties {
    private final String secretKey;
    private final long expirationTime;
    private final long refreshExpirationTime;
    private final String[] excludePath;
}
