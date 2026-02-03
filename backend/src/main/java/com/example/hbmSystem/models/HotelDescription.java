package com.example.hbmSystem.models;

import jakarta.persistence.*;

@Entity
public class HotelDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 2000)
    private String description;

    @OneToOne
    @JoinColumn(name = "hotelId", nullable = false)
    private Hotel hotel;

    private String hotelname;

    //g,s,c


    public HotelDescription() {
    }

    public HotelDescription(int id, String description, Hotel hotel,String hotelname) {
        this.id = id;
        this.description = description;
        this.hotel = hotel;
        this.hotelname= hotelname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    @Override
    public String toString() {
        return "HotelDescription{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", hotel=" + hotel +
                ", hotelname='" + hotelname + '\'' +
                '}';
    }
}
