package com.side.rest.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthTokenRequestDto
        (
                @NotBlank
                @Size(min = 1, max = 30)
                String userId,

                @NotBlank
                String password,

                @NotNull
                int days
        ) {

}
