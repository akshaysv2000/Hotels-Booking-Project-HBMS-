package com.example.hbmSystem.dto;

public class DescRespDto {
    private int id;
    private int hotelid;
    private String description;
    private String hotelName;

    public DescRespDto() {
    }

    public DescRespDto(int id, int hotelid, String description, String hotelName) {
        this.id = id;
        this.hotelid = hotelid;
        this.description = description;
        this.hotelName = hotelName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelid() {
        return hotelid;
    }

    public void setHotelid(int hotelid) {
        this.hotelid = hotelid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    @Override
    public String toString() {
        return "DescRespDto{" +
                "id=" + id +
                ", hotelid=" + hotelid +
                ", description='" + description + '\'' +
                ", hotelName='" + hotelName + '\'' +
                '}';
    }
}
