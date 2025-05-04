package com.side.infrastructure.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role_hierarchy")
@IdClass(RoleHierarchyKey.class)
public class RoleHierarchyEntity {

    @Id
    @Column(name = "lower_role")
    private String lowerRole;

    @Id
    @Column(name = "higher_role")
    private String higherRole;
}