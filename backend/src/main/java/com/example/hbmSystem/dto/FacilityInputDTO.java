package com.example.hbmSystem.dto;

public class FacilityInputDTO {

    private String facility;

    public FacilityInputDTO() {
    }

    public FacilityInputDTO(String facility) {
        this.facility = facility;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    @Override
    public String toString() {
        return "FacilityInputDTO{" +
                "facility='" + facility + '\'' +
                '}';
    }
}
