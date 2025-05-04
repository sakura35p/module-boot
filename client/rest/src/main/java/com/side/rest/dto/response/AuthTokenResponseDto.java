package com.side.rest.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthTokenResponseDto {

    private String accessToken;
    private String accessRefreshToken;
    private long expiresIn;
    private long refreshExpiresIn;

}
