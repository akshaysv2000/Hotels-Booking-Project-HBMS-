package com.example.hbmSystem.dto;

import java.math.BigDecimal;

public class HotelCardDTO {
    private int hotelId;
    private String name;
    private String location;
    private BigDecimal startingPrice;
    private String description;
    private String imageUrl;

    public HotelCardDTO() {
    }

    public HotelCardDTO(int hotelId, String name, String location, BigDecimal startingPrice, String description, String imageUrl) {
        this.hotelId = hotelId;
        this.name = name;
        this.location = location;
        this.startingPrice = startingPrice;
        this.description = description;
        this.imageUrl = imageUrl;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
