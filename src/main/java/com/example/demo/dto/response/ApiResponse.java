package com.example.demo.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {

    private Integer status;
    private String message;
    private String error;

    public static ApiResponse success() {
        return ApiResponse.builder()
                .status(HttpStatus.OK.value())
                .message("success")
                .build();
    }

    public static ApiResponse error(HttpStatus status, String message) {
        return ApiResponse.builder()
                .status(status.value())
                .message(message)
                .error(status.getReasonPhrase())
                .build();
    }
}

