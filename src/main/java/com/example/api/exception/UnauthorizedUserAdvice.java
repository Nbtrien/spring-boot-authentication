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
public class UnauthorizedUserAdvice {

    /**
     * @param ex
     * @return
     */
    @ExceptionHandler(UnauthorizedUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDto handleUnauthorizedUserException(UnauthorizedUserException ex) {
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(),
                                                         HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.addErrorKeyAndObjectDetail("exception", ex.getLocalizedMessage());

        return response;
    }

}