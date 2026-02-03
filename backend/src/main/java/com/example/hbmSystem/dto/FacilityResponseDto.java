package com.example.hbmSystem.dto;



public class FacilityResponseDto {
    private int facilityId;
    private String facility;
    private int hotelId;

    public FacilityResponseDto() { }

    public FacilityResponseDto(int facilityId, String facility, int hotelId) {
        this.facilityId = facilityId;
        this.facility = facility;
        this.hotelId = hotelId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(int facilityId) {
        this.facilityId = facilityId;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }
}

