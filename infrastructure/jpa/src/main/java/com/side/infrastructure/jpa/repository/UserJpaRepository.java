package com.side.infrastructure.jpa.repository;

import com.side.domain.enums.UserStatus;
import com.side.infrastructure.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByUserIdAndStatus(String id, UserStatus status);
}
