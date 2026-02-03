package com.example.hbmSystem.dto;

public class HotelUpdateDTO {
    private String username;
    private String password;
    private String contactNumber;


    public HotelUpdateDTO() {
    }

    public HotelUpdateDTO(String username, String password, String contactNumber) {
        this.username = username;
        this.password = password;
        this.contactNumber = contactNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "HotelUpdateDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }
}
