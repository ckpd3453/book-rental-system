package com.crio.bookrentalsystem.dto;

import org.springframework.http.HttpStatus;

public class ResponseDto {

    private HttpStatus code;
    private Object data;
    private String message;

    public ResponseDto(HttpStatus code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
