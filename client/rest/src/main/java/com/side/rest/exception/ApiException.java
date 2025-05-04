package com.side.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -2950545099839156527L;

    private final HttpStatus status;
    private final String message;

    public ApiException(HttpStatus status, String message, Throwable e) {
        super(e);
        this.status = status;
        this.message = message;
    }

    public ApiException(HttpStatus status) {
        super();
        this.status = status;
        this.message = status.getReasonPhrase();
    }

    public ApiException(String message) {
        super();
        this.status = HttpStatus.BAD_REQUEST;
        this.message = message;
    }

}
