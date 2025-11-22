package com.example.demo.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {

    private Integer status;
    private Object data;
    private String error;

    public static ApiResponse success() {
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .build();
    }

    public static ApiResponse success(Object data) {
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .data(data)
                .build();
    }

    public static ApiResponse error(HttpStatus status, String message) {
        return ApiResponse.builder()
                .status(status.value())
                .error(message)
                .build();
    }
}

