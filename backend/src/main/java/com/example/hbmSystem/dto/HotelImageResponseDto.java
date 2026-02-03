package com.example.hbmSystem.dto;


public class HotelImageResponseDto {
    private int imageId;
    private String imageUrl; // relative path, frontend can use to load images

    public HotelImageResponseDto() {
    }

    public HotelImageResponseDto(int imageId, String imageUrl) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

