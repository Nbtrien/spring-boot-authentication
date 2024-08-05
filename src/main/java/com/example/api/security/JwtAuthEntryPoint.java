package com.example.api.security;

import com.example.api.dto.ErrorResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        System.out.println(e.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setHeader("content-type", "application/json");
        ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        String responseMsg = objectMapper.writeValueAsString(responseDto);
        response.getWriter().write(responseMsg);
    }
}