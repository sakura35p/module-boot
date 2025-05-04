package com.side.infrastructure.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Embeddable
public class ArticleEntity {

    @Column(name = "title", length = 30)
    private String title;

    @Lob
    @Column(name = "contents")
    private String contents;

    @Column(name = "likes")
    private int likes;

    @Column(name = "dislikes")
    private int dislikes;
}
