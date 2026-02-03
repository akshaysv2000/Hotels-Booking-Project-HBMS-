package com.example.hbmSystem.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    private String roomType;

    private BigDecimal pricePerNight;

    private int totalRooms;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Booking> booking;

    public boolean isAvailable(LocalDate startDate, LocalDate endDate) {
        if (booking == null || booking.isEmpty()){
            return true;
        }
        long activeBookings = booking.stream()
                .filter(b -> b.getStatus() == Booking.Status.Booked &&
                        startDate.isBefore(b.getCheckOutDate()) &&
                        b.getCheckInDate().isBefore(endDate))
                .count();
        return activeBookings < totalRooms;
    }


    //getters Setters Constructors


    public Room() {
    }

    public Room(int roomId, Hotel hotel, String roomType, BigDecimal pricePerNight, int totalRooms, List<Booking> booking) {
        this.roomId = roomId;
        this.hotel = hotel;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
        this.totalRooms = totalRooms;
        this.booking = booking;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
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

    public List<Booking> getBooking() {
        return booking;
    }

    public void setBooking(List<Booking> booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", hotel=" + hotel +
                ", roomType='" + roomType + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", totalRooms=" + totalRooms +
                ", booking=" + booking +
                '}';
    }
}
