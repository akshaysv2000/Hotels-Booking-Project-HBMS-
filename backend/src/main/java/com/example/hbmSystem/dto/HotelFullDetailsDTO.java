package com.example.hbmSystem.dto;

import java.math.BigDecimal;
import java.util.List;

public class HotelFullDetailsDTO  {
    public int hotelId;
    public String name;
    public String location;
    public String address;
    public String contactNumber;
    public String description;
    public List<String> imageUrls;
    public List<String> facilities;
    public List<RoomTypeDTO> rooms;
    public List<ReviewDTO> reviews;



    public static class RoomTypeDTO {
        public int roomId;
        public String roomType;
        public java.math.BigDecimal pricePerNight;
        public int totalRooms;

        public RoomTypeDTO() {
        }

        public int getRoomId() {
            return roomId;
        }

        public void setRoomId(int roomId) {
            this.roomId = roomId;
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
    }
    public static class ReviewDTO {
        public String username;
        public int rating;
        public String comment;
        public String date;
        // getters and setters


        public ReviewDTO() {
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public HotelFullDetailsDTO() {
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

    public List<RoomTypeDTO> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomTypeDTO> rooms) {
        this.rooms = rooms;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
}
