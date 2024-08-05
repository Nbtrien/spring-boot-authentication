package com.example.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class ErrorResponseDto {
    private static final String KEY_ID = "id";
    private static final String KEY_MESSAGE = "message";
    private Integer code;
    private String message;
    private final List<Map<String, Object>> details = new ArrayList<>();

    public ErrorResponseDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addErrorKeyAndObjectDetail(String key, Object value) {
        Map<String, Object> errorMap = new HashMap<>() {{
            put(key, value);
        }};
        details.add(errorMap);
    }
}
