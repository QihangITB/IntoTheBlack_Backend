package com.intotheblack.itb_api.dto;

public class MessageRequestDTO {
    private String message;

    public MessageRequestDTO() {
    }

    public MessageRequestDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
