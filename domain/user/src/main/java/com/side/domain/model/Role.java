package com.side.domain.model;

import com.side.domain.Metadata;
import lombok.Builder;

@Builder(toBuilder = true)
public record Role(
        String roleId,
        String roleName,
        String description,
        Metadata metadata
) {
}
