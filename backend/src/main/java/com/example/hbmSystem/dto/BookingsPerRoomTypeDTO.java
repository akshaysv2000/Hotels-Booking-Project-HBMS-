package com.example.hbmSystem.dto;

public class BookingsPerRoomTypeDTO {
    private String roomType;
    private long count;

    public BookingsPerRoomTypeDTO() {
    }

    public BookingsPerRoomTypeDTO(String roomType, long count) {
        this.roomType = roomType;
        this.count = count;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
