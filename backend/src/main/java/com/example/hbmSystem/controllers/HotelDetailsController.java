package com.example.hbmSystem.controllers;

import com.example.hbmSystem.dto.HotelFullDetailsDTO;
import com.example.hbmSystem.models.Hotel;
import com.example.hbmSystem.models.HotelFacility;
import com.example.hbmSystem.repository.HotelFacilityRepository;
import com.example.hbmSystem.repository.HotelImageRepository;
import com.example.hbmSystem.repository.HotelRepository;
import com.example.hbmSystem.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/hotels")
public class HotelDetailsController {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelImageRepository hotelImageRepository;
    @Autowired
    private HotelFacilityRepository hotelFacilityRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/{hotelId}/details")
    public HotelFullDetailsDTO getHotelFullDetails(@PathVariable int hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + hotelId));

        // Images
        List<String> imageUrls = hotelImageRepository.findByHotel_HotelId(hotelId)
                .stream()
                .map(img -> {
                    String storedPath = img.getImageUrl();
                    String filenameOnly = storedPath.contains("/") ?
                            storedPath.substring(storedPath.indexOf("/") + 1) : storedPath;
                    return "http://localhost:8080/user/hotel-images/" + hotelId + "/" + filenameOnly;
                })
                .collect(Collectors.toList());

        // Facilities
        List<String> facilityList = hotelFacilityRepository.findByHotel_HotelId(hotelId)
                .stream()
                .map(HotelFacility::getFacility)
                .collect(Collectors.toList());

        // Room types
        List<HotelFullDetailsDTO.RoomTypeDTO> roomTypes = hotel.getRooms().stream().map(room -> {
            HotelFullDetailsDTO.RoomTypeDTO roomDto = new HotelFullDetailsDTO.RoomTypeDTO();
            roomDto.roomId = room.getRoomId();
            roomDto.roomType = room.getRoomType();
            roomDto.pricePerNight = room.getPricePerNight();
            roomDto.totalRooms = room.getTotalRooms();
            return roomDto;
        }).collect(Collectors.toList());

        // Description
        String description = (hotel.getHotelDescription() != null) ?
                hotel.getHotelDescription().getDescription() : "";

        // Reviews
        List<HotelFullDetailsDTO.ReviewDTO> reviews = reviewRepository
                .findByHotel_HotelIdOrderByReviewDateDesc(hotelId)
                .stream().map(r -> {
                    HotelFullDetailsDTO.ReviewDTO dto = new HotelFullDetailsDTO.ReviewDTO();
                    dto.username = r.getUser() != null ? r.getUser().getUsername() : "Anonymous";
                    dto.rating = r.getRating();
                    dto.comment = r.getComment();
                    dto.date = r.getReviewDate() != null ? r.getReviewDate().toString() : null;
                    return dto;
                }).collect(Collectors.toList());


        // Set all values in DTO
        HotelFullDetailsDTO dto = new HotelFullDetailsDTO();
        dto.setHotelId(hotelId);
        dto.setName(hotel.getName());
        dto.setLocation(hotel.getLocation());
        dto.setAddress(hotel.getAddress());
        dto.setContactNumber(hotel.getContactNumber());
        dto.setDescription(description);
        dto.setImageUrls(imageUrls);
        dto.setFacilities(facilityList);
        dto.setRooms(roomTypes);
        dto.setReviews(reviews);

        return dto;
    }
}
