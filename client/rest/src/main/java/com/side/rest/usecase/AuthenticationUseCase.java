package com.side.rest.usecase;


import com.side.domain.memory.constants.RedisKeyNames;
import com.side.domain.memory.service.RedisService;
import com.side.rest.dto.request.AuthTokenRequestDto;
import com.side.rest.dto.response.AuthTokenResponseDto;
import com.side.rest.exception.ApiException;
import com.side.security.jwt.dto.SecurityDto;
import com.side.security.jwt.service.JwtService;
import com.side.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AuthenticationUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SecurityService securityService;
    private final RedisService redisService;

    public SecurityDto authenticate(String userId, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userId,
                        password
                )
        );

        return (SecurityDto) authentication.getPrincipal();
    }

    public AuthTokenResponseDto generateToken(AuthTokenRequestDto authTokenRequestDto) {

        String userId = authTokenRequestDto.userId();
        SecurityDto securityDto = authenticate(userId, authTokenRequestDto.password());

        String token = jwtService.generateToken(securityDto, 0);
        String refreshToken = jwtService.generateRefreshToken(securityDto, 0);

        this.createRefreshTokenInRedis(userId, refreshToken);

        return AuthTokenResponseDto.builder()
                                   .accessToken(token)
                                   .accessRefreshToken(refreshToken)
                                   .expiresIn(jwtService.getExpirationSecond())
                                   .refreshExpiresIn(jwtService.getRefreshExpirationSecond())
                                   .build();
    }

    public AuthTokenResponseDto generateWithExpirationToken(AuthTokenRequestDto authTokenRequestDto) {

        SecurityDto securityDTO = authenticate(authTokenRequestDto.userId(), authTokenRequestDto.password());

        int day = authTokenRequestDto.days();

        String token = jwtService.generateToken(securityDTO, day);
        String refreshToken = jwtService.generateRefreshToken(securityDTO, day);

        long expirationInMillis = TimeUnit.DAYS.toMillis(day) / 1000;

        return AuthTokenResponseDto.builder()
                                   .accessToken(token)
                                   .accessRefreshToken(refreshToken)
                                   .expiresIn(jwtService.getExpirationSecond() + expirationInMillis)
                                   .refreshExpiresIn(jwtService.getRefreshExpirationSecond() + expirationInMillis)
                                   .build();
    }

    public AuthTokenResponseDto refreshToken(String providedRefreshToken, AuthTokenRequestDto authTokenRequestDto) {

        String userId = authTokenRequestDto.userId();
        String ourOwnRefreshToken = redisService.get(RedisKeyNames.JWT_REFRESH_TOKEN + userId, String.class);

        if (!providedRefreshToken.equals(ourOwnRefreshToken)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다.");
        }

        SecurityDto securityDto = securityService.loadUserByUsername(userId);

        String token = jwtService.generateToken(securityDto, 0);
        String newRefreshToken = jwtService.generateRefreshToken(securityDto, 0);

        createRefreshTokenInRedis(userId, newRefreshToken);

        return AuthTokenResponseDto.builder()
                                   .accessToken(token)
                                   .accessRefreshToken(newRefreshToken)
                                   .expiresIn(jwtService.getExpirationSecond())
                                   .refreshExpiresIn(jwtService.getRefreshExpirationSecond())
                                   .build();
    }

    public void createRefreshTokenInRedis(String userId, String refreshToken) {
        String key = RedisKeyNames.JWT_REFRESH_TOKEN + userId;
        redisService.create(key, refreshToken, jwtService.getRefreshExpirationSecond(), TimeUnit.SECONDS);
    }

}
