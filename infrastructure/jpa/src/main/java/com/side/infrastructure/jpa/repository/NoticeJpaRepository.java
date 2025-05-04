package com.side.infrastructure.jpa.repository;

import com.side.infrastructure.jpa.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoticeJpaRepository extends JpaRepository<NoticeEntity, Long> {
    Optional<NoticeEntity> findByArticle_Title(String name);
}
