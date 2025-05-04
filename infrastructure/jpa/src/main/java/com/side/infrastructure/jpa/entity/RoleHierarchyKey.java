package com.side.infrastructure.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class RoleHierarchyKey implements Serializable {

    @Serial
    private static final long serialVersionUID = -5285826462359427736L;

    private String lowerRole;
    private String higherRole;
}