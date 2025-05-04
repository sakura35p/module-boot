package com.side.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
public class ArticleResponseDto {

    private String title;

    private String contents;

    private int likes;

    private int dislikes;
}