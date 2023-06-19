package com.practicalinterview.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseResponse {
    private Object response;
    private int status;
    private String message;

    public BaseResponse(Object response) {
        this.response = response;
        this.status = 200;
        this.message = "Success";
    }

    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
