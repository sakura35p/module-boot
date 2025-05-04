package com.side.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@AllArgsConstructor
@Builder
public class MetadataResponseDto {

    private String createdBy;

    private Instant createdAt;

    private String modifiedBy;

    private Instant modifiedAt;
}