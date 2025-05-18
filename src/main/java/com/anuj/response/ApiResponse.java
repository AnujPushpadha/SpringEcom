package com.anuj.response;

import lombok.Data;
@Data
public class ApiResponse {

    private String message;

    public ApiResponse(String orderDeletedSuccessfully, boolean b) {
    }
}
