package com.side.domain;

import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true)
public record Metadata(String createdBy,
                       Instant createdAt,
                       String modifiedBy,
                       Instant modifiedAt) {
}