package com.example.hbmSystem.dto;

public class AdminEditRequest {
    private String newUsername;
    private String newPassword;

    public AdminEditRequest() {
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "AdminEditRequest{" +
                "newUsername='" + newUsername + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }


}
