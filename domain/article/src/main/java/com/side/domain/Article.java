package com.side.domain;

import lombok.Builder;

@Builder(toBuilder = true)
public record Article(String title, String contents, int likes, int dislikes) {
}