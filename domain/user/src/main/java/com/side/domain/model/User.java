package com.side.domain.model;

import com.side.domain.Metadata;
import com.side.domain.enums.UserStatus;
import com.side.domain.enums.UserType;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record User(
        String id,
        String password,
        String name,
        UserStatus status,
        UserType type,
        List<Role> roles,
        Metadata metadata
) {
}