package com.example.hbmSystem.dto;

public class HotelInfoDto {
    private int hotelId;
    private String name;
    // minimal hotel information

    // getters and setters

    public HotelInfoDto() {
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

    @Override
    public String toString() {
        return "HotelInfoDto{" +
                "hotelId=" + hotelId +
                ", name='" + name + '\'' +
                '}';
    }
}
