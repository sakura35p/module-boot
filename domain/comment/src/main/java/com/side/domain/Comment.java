package com.side.domain;

import lombok.Builder;

@Builder(toBuilder = true)
public record Comment(String contents, int likes, int dislikes) {
}