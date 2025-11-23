package com.example.demo.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {

    private Integer status;
    private String message;
    private String error;
    private Map<String, Object> data;

    public static ApiResponse success(Map<String, Object> data) {
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .data(data)
                .build();
    }

    public static ApiResponse error(HttpStatus status, String message) {
        return ApiResponse.builder()
                .status(status.value())
                .message(message)
                .error(status.getReasonPhrase())
                .data(new HashMap<>())
                .build();
    }
}

