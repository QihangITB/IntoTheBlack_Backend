package com.intotheblack.itb_api.dto;

public class PasswordRequestDTO {

    private String oldP;
    private String newP;

    // Constructor
    public PasswordRequestDTO() {
    }
    
    public PasswordRequestDTO(String oldP, String newP) {
        this.oldP = oldP;
        this.newP = newP;
    }

    // Getters y setters
    public String getOldPassword() {
        return oldP;
    }

    public void setOldPassword(String oldP) {
        this.oldP = oldP;
    }

    public String getNewPassword() {
        return newP;
    }

    public void setNewPassword(String newP) {
        this.newP = newP;
    }
}
