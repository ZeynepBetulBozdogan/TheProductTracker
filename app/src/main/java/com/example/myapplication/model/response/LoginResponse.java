package com.example.myapplication.model.response;

public class LoginResponse {
    String status;
    String message;
    String type;

    public String getStatus() {
        return status;
    }

    public LoginResponse(String status, String message, String type, String token) {
        this.status = status;
        this.message = message;
        this.type = type;
        this.token = token;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    String token;

}
