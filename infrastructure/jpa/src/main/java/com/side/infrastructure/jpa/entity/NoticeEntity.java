package com.side.infrastructure.jpa.entity;

import com.side.infrastructure.jpa.common.MetadataEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "notice")
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private ArticleEntity article;

    @Transient
    private CommentEntity comment;

    @Embedded
    private MetadataEntity metadata;
}
