package com.example.api.exception;

import com.example.api.dto.ErrorResponseDto;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CommonExceptionHandler {
    private static final String PATTERN = "^requestDto\\.(.+)$";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleMethodNotValidException(MethodArgumentNotValidException ex) {

        ErrorResponseDto error = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),
                                                      HttpStatus.BAD_REQUEST.getReasonPhrase());

        for (final FieldError field : ex.getBindingResult().getFieldErrors()) {
            final String name = field.getField().replaceAll(PATTERN, "$1");
            error.addErrorKeyAndObjectDetail(name, field.getDefaultMessage());
        }
        return error;
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleInvalidFormatException(InvalidFormatException ex) {
        ErrorResponseDto error = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),
                                                      HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.addErrorKeyAndObjectDetail("exception", ex.getLocalizedMessage());
        return error;
    }

    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleMethodNotValidException(MissingPathVariableException ex) {

        ErrorResponseDto error = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),
                                                      HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.addErrorKeyAndObjectDetail("exception", "miss path param :" + ex.getVariableName());

        return error;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleMessageNotReadableException(HttpMessageNotReadableException ex) {

        ErrorResponseDto error = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),
                                                      HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.addErrorKeyAndObjectDetail("exception", "Request body is invalid.");

        return error;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),
                                                         HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.addErrorKeyAndObjectDetail("exception", ex.getLocalizedMessage());

        return response;

    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleBadCredentialsException(BadCredentialsException ex) {
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),
                                                         HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.addErrorKeyAndObjectDetail("exception", ex.getLocalizedMessage());

        return response;
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponseDto handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.FORBIDDEN.value(),
                                                         HttpStatus.FORBIDDEN.getReasonPhrase());
        response.addErrorKeyAndObjectDetail("exception", ex.getLocalizedMessage());

        return response;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(),
                                                         HttpStatus.NOT_FOUND.getReasonPhrase());
        response.addErrorKeyAndObjectDetail("exception", ex.getLocalizedMessage());

        return response;
    }
}