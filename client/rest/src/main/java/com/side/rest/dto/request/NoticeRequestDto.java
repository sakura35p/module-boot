package com.side.rest.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeRequestDto {

    private Long id;

    private ArticleRequestDto article;
}