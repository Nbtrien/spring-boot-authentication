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
public class InvalidTokenAdvice {
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto handleInvalidTokenException(InvalidTokenException ex) {
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.FORBIDDEN.value(),
                                                         HttpStatus.FORBIDDEN.getReasonPhrase());
        response.addErrorKeyAndObjectDetail("exception", ex.getLocalizedMessage());

        return response;
    }
}
