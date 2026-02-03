package com.example.hbmSystem.dto;

import java.math.BigDecimal;

public class RoomDto {
    private String roomType;

    private BigDecimal pricePerNight;

    private int totalRooms;

    public RoomDto() {
    }

    public RoomDto(String roomType, BigDecimal pricePerNight, int totalRooms) {
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.totalRooms = totalRooms;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
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
        return "RoomDto{" +
                "roomType='" + roomType + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", totalRooms=" + totalRooms +
                '}';
    }
}
