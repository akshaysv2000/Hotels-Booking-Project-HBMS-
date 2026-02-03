package com.example.hbmSystem.dto;

import java.math.BigDecimal;

public class RoomResponseDto {
    private int roomId;
    private String roomType;
    private BigDecimal pricePerNight;

    private int totalRooms;
    // other room fields

    private HotelInfoDto hotel;

    // getters and setters


    public RoomResponseDto() {
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public HotelInfoDto getHotel() {
        return hotel;
    }

    public void setHotel(HotelInfoDto hotel) {
        this.hotel = hotel;
    }

    public RoomResponseDto(int roomId, String roomType, BigDecimal pricePerNight, int totalRooms, HotelInfoDto hotel) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.totalRooms = totalRooms;
        this.hotel = hotel;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public int getTotalRooms() {
        return totalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        this.totalRooms = totalRooms;
    }

    @Override
    public String toString() {
        return "RoomResponseDto{" +
                "roomId=" + roomId +
                ", roomType='" + roomType + '\'' +
                ", hotel=" + hotel +
                '}';
    }
}
