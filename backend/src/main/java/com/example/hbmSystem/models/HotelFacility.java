package com.example.hbmSystem.models;

import jakarta.persistence.*;

@Entity
public class HotelFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int facilityId;

    @Column(length = 100, nullable = false)
    private String facility;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    public HotelFacility() {
    }

    public HotelFacility(int facilityId, String facility, Hotel hotel) {
        this.facilityId = facilityId;
        this.facility = facility;
        this.hotel = hotel;
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public String toString() {
        return "HotelFacility{" +
                "facilityId=" + facilityId +
                ", facility='" + facility + '\'' +
                ", hotelId=" + (hotel != null ? hotel.getHotelId() : null) +
                '}';
    }

}
