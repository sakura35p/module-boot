package com.side.infrastructure.jpa.repository;

import com.side.infrastructure.jpa.entity.RoleHierarchyEntity;
import com.side.infrastructure.jpa.entity.RoleHierarchyKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleHierarchyJpaRepository extends JpaRepository<RoleHierarchyEntity, RoleHierarchyKey> {
}