package com.side.security.jwt.service;

import com.side.domain.GenericClassToken;
import com.side.security.jwt.claims.JwtClaims;
import com.side.security.jwt.config.JwtProperties;
import com.side.security.jwt.dto.SecurityDto;
import com.side.security.jwt.enums.JwtTokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final String TOKEN_TYPE = "token_type";
    private final String ROLES = "roles";

    private final JwtProperties jwtProperties;

    public long getExpirationSecond() {
        return jwtProperties.getExpirationTime() / 1000;
    }

    public long getRefreshExpirationSecond() {
        return jwtProperties.getRefreshExpirationTime() / 1000;
    }

    public long getExpirationTime() {
        return jwtProperties.getExpirationTime();
    }

    public long getRefreshExpirationTime() {
        return jwtProperties.getRefreshExpirationTime();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtBuilder jwtbuilder(SecurityDto securityDto, long time, String tokenType) {
        return Jwts.builder()
                   .claim(TOKEN_TYPE, tokenType)
                   .subject(securityDto.getUserId())
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + time))
                   .signWith(getSignInKey())
                   .header().type("jwt")
                   .and();
    }

    public String generateToken(SecurityDto securityDto, int days) {
        long expirationInMillis = TimeUnit.DAYS.toMillis(days);
        return jwtbuilder(securityDto, expirationInMillis + getExpirationTime(), JwtTokenType.ACCESS.name())
                .claim(ROLES, securityDto.getAuthorities().stream().map(Objects::toString).toList())
                .compact();
    }

    public String generateRefreshToken(SecurityDto securityDto, int days) {
        long expirationInMillis = TimeUnit.DAYS.toMillis(days);
        return jwtbuilder(securityDto, expirationInMillis + getRefreshExpirationTime(), JwtTokenType.REFRESH.name())
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                   .verifyWith(getSignInKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload();
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> claims.get(
                ROLES,
                new GenericClassToken<List<String>>() {}.getRawType())
        );
    }

    public JwtTokenType extractTokenType(String token) {
        String tokenType = extractClaim(token, claims -> claims.get(TOKEN_TYPE, String.class));
        return JwtTokenType.valueOf(tokenType.toUpperCase());
    }

    public boolean isAccessToken(String token) {
        return JwtTokenType.ACCESS == extractTokenType(token);
    }

    public boolean isRefreshToken(String token) {
        return JwtTokenType.REFRESH == extractTokenType(token);
    }

    public JwtClaims getJwtClaims(String token) {
        Claims claims = extractAllClaims(token);
        return JwtClaims.builder()
                        .userId(claims.getSubject())
                        .tokenType(JwtTokenType.valueOf(claims.get(TOKEN_TYPE, String.class).toUpperCase()))
                        .roles(claims.get(ROLES, new GenericClassToken<List<String>>() {}.getRawType()))
                        .build();
    }

}
