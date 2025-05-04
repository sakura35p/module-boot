package com.side.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class NoticeResponseDto {

    private Long id;

    private ArticleResponseDto article;

    private CommentResponseDto comment;

    private MetadataResponseDto metadata;
}
