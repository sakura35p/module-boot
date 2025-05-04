package com.side.rest.controller;

import com.side.domain.memory.service.RedisService;
import com.side.rest.dto.ApiResponse;
import com.side.rest.dto.request.NoticeRequestDto;
import com.side.rest.dto.response.NoticeResponseDto;
import com.side.rest.usecase.NoticeUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeUseCase noticeUseCase;

    private final RedisService redisService;

    @PostMapping("/test")
    public ResponseEntity<?> create1(@RequestBody NoticeRequestDto noticeRequestDto) {
        log.info("Request received - ID: {}", noticeRequestDto.getId());
        
        try {
            // Redis에 객체 자체를 저장
            redisService.create(String.valueOf(noticeRequestDto.getId()), noticeRequestDto);
            log.info("Object saved to Redis with key: {}", noticeRequestDto.getId());
            
            // Redis에서 객체 가져오기
            NoticeResponseDto retrieved = redisService.get(String.valueOf(noticeRequestDto.getId()), NoticeResponseDto.class);
            log.info("Retrieved from Redis: {}", retrieved);
            
            return ResponseEntity
                .ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body(retrieved);
        } catch (Exception e) {
            log.error("Error in test endpoint: {}", e.getMessage(), e);
            return ResponseEntity
                .status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody NoticeRequestDto sideRequestDto) {

        noticeUseCase.create(sideRequestDto);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ApiResponse<NoticeResponseDto> get(NoticeRequestDto noticeRequestDto) {

        return ApiResponse.of(NoticeResponseDto.builder().id(2L).build());
    }
}
