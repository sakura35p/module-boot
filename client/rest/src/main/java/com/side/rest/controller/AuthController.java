package com.side.rest.controller;

import com.side.rest.dto.request.AuthTokenRequestDto;
import com.side.rest.dto.response.AuthTokenResponseDto;
import com.side.rest.usecase.AuthenticationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationUseCase service;

    @PostMapping(value = "/token")
    public ResponseEntity<AuthTokenResponseDto> token(@RequestBody @Validated AuthTokenRequestDto authTokenRequestDto) {

        return ResponseEntity.ok(service.generateToken(authTokenRequestDto));
    }

    @PostMapping(value = "/expiration/token")
    public ResponseEntity<AuthTokenResponseDto> expirationToken(@RequestBody @Validated AuthTokenRequestDto authTokenRequestDto) {

        return ResponseEntity.ok(service.generateWithExpirationToken(authTokenRequestDto));
    }

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<AuthTokenResponseDto> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken,
                                                             @RequestBody AuthTokenRequestDto authTokenRequestDto) {

        return ResponseEntity.ok(service.refreshToken(refreshToken, authTokenRequestDto));
    }


}