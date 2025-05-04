package com.side.domain.model;

import com.side.domain.Article;
import com.side.domain.Comment;
import com.side.domain.Metadata;
import lombok.Builder;

@Builder(toBuilder = true)
public record Notice(Long id, Article article, Comment comment, Metadata metadata) {
}