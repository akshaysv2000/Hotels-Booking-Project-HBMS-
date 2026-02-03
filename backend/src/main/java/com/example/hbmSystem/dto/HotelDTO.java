package com.example.hbmSystem.dto;

import java.time.LocalDateTime;

public class HotelDTO {
    private int hotelId;

    private String name;
    private String address;
    private String ownerName;
    private String contactNumber;
    private String status;
    private LocalDateTime createdAt;
    private String location;

    public HotelDTO() {
    }

    public HotelDTO(int hotelId,  String name, String address, String ownerName, String contactNumber, String status, LocalDateTime createdAt) {
        this.hotelId = hotelId;

        this.name = name;
        this.address = address;
        this.ownerName = ownerName;
        this.contactNumber = contactNumber;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "HotelDTO{" +
                "hotelId=" + hotelId +

                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
