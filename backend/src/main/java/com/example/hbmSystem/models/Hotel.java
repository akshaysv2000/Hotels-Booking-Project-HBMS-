package com.example.hbmSystem.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hotelId;
    @Column(unique = true,nullable = false)
    private String username;
    private String password;
    private String name;
    private String address;
    private String ownerName;
    private String contactNumber;

    private String location;

    private boolean detailsCompleted = false;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "hotel",cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "hotel",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToOne(mappedBy = "hotel",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private HotelDescription hotelDescription;

    @OneToMany(mappedBy = "hotel",fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    private List<HotelFacility> hotelFacilities;


    public enum Status{
        Pending,Approved,Rejected
    }
    @PrePersist
    protected void onCreate(){
        if (status == null){
            status=Status.Pending;
        }
        createdAt=LocalDateTime.now();
    }

    private Double latitude;
    private Double longitude;

    //getters Setters Constructors


    public Hotel() {
    }

    public Hotel(int hotelId, String username, String password, String name, String address, String ownerName, String contactNumber, Status status, LocalDateTime createdAt, List<Room> rooms, List<Booking> bookings, List<Review> reviews, HotelDescription hotelDescription, List<HotelFacility> hotelFacilities) {
        this.hotelId = hotelId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        this.ownerName = ownerName;
        this.contactNumber = contactNumber;
        this.status = status;
        this.createdAt = createdAt;
        this.rooms = rooms;
        this.bookings = bookings;
        this.reviews = reviews;
        this.hotelDescription = hotelDescription;
        this.hotelFacilities = hotelFacilities;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public HotelDescription getHotelDescription() {
        return hotelDescription;
    }

    public void setHotelDescription(HotelDescription hotelDescription) {
        this.hotelDescription = hotelDescription;
    }

    public List<HotelFacility> getHotelFacilities() {
        return hotelFacilities;
    }

    public void setHotelFacilities(List<HotelFacility> hotelFacilities) {
        this.hotelFacilities = hotelFacilities;
    }

    public boolean isDetailsCompleted() {
        return detailsCompleted;
    }

    public void setDetailsCompleted(boolean detailsCompleted) {
        this.detailsCompleted = detailsCompleted;
    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", rooms=" + rooms +
                ", bookings=" + bookings +
                ", reviews=" + reviews +
                ", hotelDescription=" + hotelDescription +
                ", hotelFacilities=" + hotelFacilities +
                '}';
    }
}
