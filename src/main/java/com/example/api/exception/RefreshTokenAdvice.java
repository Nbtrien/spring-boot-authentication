package com.example.api.exception;

import com.example.api.dto.ErrorResponseDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RefreshTokenAdvice {
    @ExceptionHandler(RefreshTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ErrorResponseDto handleRefreshTokenException(RefreshTokenException ex) {
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),
                                                         HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.addErrorKeyAndObjectDetail("exception", ex.getLocalizedMessage());

        return response;
    }

}