package com.side.rest.restclient.client;

import com.side.rest.dto.ApiResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.Map;

public interface BaseClient {

    @PostExchange("/test1")
    ResponseEntity<ApiResponse<Map<String, Object>>> test1(@RequestBody Map<String, Object> carEnteredRequest);

    @PostExchange("/test2")
    ResponseEntity<ApiResponse<Map<String, Object>>> test2(@RequestBody Map<String, Object> carExitedTpmsRequest);

    @PostExchange(value = "/test3", contentType = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ApiResponse<Map<String, Object>>> test3(@RequestPart Resource multipartFile, @RequestPart Map<String, Object> reconImageUploadRequest);

    @PostExchange(value = "/test4", contentType = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ApiResponse<Map<String, Object>>> test4(@RequestPart Resource multipartFile, @RequestPart Map<String, Object> reconImageUploadRequest);

    @PostExchange("/test5")
    ResponseEntity<ApiResponse<Map<String, Object>>> test5(@RequestHeader(HttpHeaders.AUTHORIZATION) String secretKey,
                                                           @RequestBody Map<String, Object> request);

    @GetExchange("/test6")
    ResponseEntity<ApiResponse<Map<String, Object>>> test6(@RequestHeader(HttpHeaders.AUTHORIZATION) String secretKey,
                                                           @RequestBody Map<String, Object> request);
}
