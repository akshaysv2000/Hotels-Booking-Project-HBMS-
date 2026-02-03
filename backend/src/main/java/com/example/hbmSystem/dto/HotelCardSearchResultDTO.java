package com.example.hbmSystem.dto;

import java.util.List;

public class HotelCardSearchResultDTO {
    private List<HotelCardDTO> byLocation;
    private List<HotelCardDTO> byName;

    public HotelCardSearchResultDTO() {
    }

    public HotelCardSearchResultDTO(List<HotelCardDTO> byLocation, List<HotelCardDTO> byName) {
        this.byLocation = byLocation;
        this.byName = byName;
    }

    public List<HotelCardDTO> getByLocation() {
        return byLocation;
    }

    public void setByLocation(List<HotelCardDTO> byLocation) {
        this.byLocation = byLocation;
    }

    public List<HotelCardDTO> getByName() {
        return byName;
    }

    public void setByName(List<HotelCardDTO> byName) {
        this.byName = byName;
    }
}
