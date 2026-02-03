package com.example.hbmSystem.controllers;

import com.example.hbmSystem.Security.CustomHotelDetails;
import com.example.hbmSystem.dto.*;
import com.example.hbmSystem.exception.ResourceNotFoundException;
import com.example.hbmSystem.models.*;
import com.example.hbmSystem.repository.BookingRepository;
import com.example.hbmSystem.service.HotelServiceImplementation;
import com.example.hbmSystem.service.Jwtutil;
import com.example.hbmSystem.service.ReviewService;
import com.example.hbmSystem.service.RoomService;
import com.example.hbmSystem.util.ResponseStructure;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class HotelController {
    @Autowired
    private HotelServiceImplementation hotelService;
    @Autowired
    ResponseStructure<Hotel> response;
    @Autowired
    private Jwtutil jwtutil;
    @Autowired
    @Qualifier("hotelAuthenticationManager")
    public AuthenticationManager authenticationManager;
    @Autowired
    public RoomService roomService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ReviewService reviewService;
    

    @PostMapping("/hotelRegistration")
    public ResponseEntity<ResponseStructure<Hotel>> hotelReg(@RequestBody Hotel newHotel){
        ResponseStructure<Hotel> save = hotelService.hotelRegister(newHotel);
        return new ResponseEntity<ResponseStructure<Hotel>>(save, HttpStatus.OK);
    }

    @PostMapping("/hotel/login")
    public ResponseEntity<?> hotelLogin(@RequestBody LoginRequest request){
        Hotel hotelEntity = hotelService.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
        if (hotelEntity.getStatus() == Hotel.Status.Pending) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "Approval pending. Your hotel registration is not approved yet."));
        } else if (hotelEntity.getStatus() == Hotel.Status.Rejected) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Collections.singletonMap("message", "Your hotel registration has been rejected."));
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails hotel = hotelService.loadUserByUsername(request.getUsername());
        String token = jwtutil.generateToken(hotel);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/hotel/status")
    public ResponseEntity<Boolean> getHotelDetailsCompletionStatus(Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomHotelDetails hotelDetails = (CustomHotelDetails) authentication.getPrincipal();
        int hotelId = hotelDetails.getHotelId();
        Hotel hotel = hotelService.findById(hotelId);
        return ResponseEntity.ok(hotel.isDetailsCompleted());
    }


    @PostMapping("/hotel/rooms")
    public ResponseEntity<RoomResponseDto> addRoom(@RequestBody RoomDto roomDto, Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomHotelDetails hotelDetails = (CustomHotelDetails) authentication.getPrincipal();
        int hotelId = hotelDetails.getHotelId();
        Room room = roomService.addRoomToHotel(hotelId, roomDto);
        RoomResponseDto roomResponseDto = convertToDto(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomResponseDto);

    }

    private RoomResponseDto convertToDto(Room room) {
        RoomResponseDto dto = new RoomResponseDto();
        dto.setRoomId(room.getRoomId());
        dto.setRoomType(room.getRoomType());
        dto.setPricePerNight(room.getPricePerNight());
        dto.setTotalRooms(room.getTotalRooms());


        HotelInfoDto hotelDto = new HotelInfoDto();
        hotelDto.setHotelId(room.getHotel().getHotelId());
        hotelDto.setName(room.getHotel().getName());


        dto.setHotel(hotelDto);
        return dto;
    }

    @PostMapping("/hotel/description")
    public ResponseEntity<DescRespDto> addHotelDescription(@RequestBody DescInputDto descInputDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomHotelDetails hotelDetails = (CustomHotelDetails) authentication.getPrincipal();
        int hotelId = hotelDetails.getHotelId();
        HotelDescription hotelDescription=new HotelDescription();
        hotelDescription.setDescription(descInputDto.getDescription());
        HotelDescription hotelDescription1 = hotelService.addHotelDescription(hotelId, hotelDescription);
       DescRespDto descRespDto=new DescRespDto();
       descRespDto.setId(hotelDescription1.getId());
       descRespDto.setHotelid(hotelId);
       descRespDto.setHotelName(hotelDescription1.getHotelname());
       descRespDto.setDescription(hotelDescription1.getDescription());
       return ResponseEntity.status(HttpStatus.CREATED).body(descRespDto);

    }

    @PostMapping("/hotel/facilities")
    public ResponseEntity<FacilityResponseDto> addFacility(@RequestBody FacilityInputDTO facilityInputDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomHotelDetails hotelDetails = (CustomHotelDetails) authentication.getPrincipal();
        int hotelId = hotelDetails.getHotelId();
        HotelFacility savedFacility = hotelService.addFacilityToHotel(hotelId, facilityInputDto);
        FacilityResponseDto responseDto = new FacilityResponseDto(
                savedFacility.getFacilityId(),
                savedFacility.getFacility(),
                hotelId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/facilities")
    public ResponseEntity<List<FacilityResponseDto>> getFacilities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomHotelDetails hotelDetails = (CustomHotelDetails) authentication.getPrincipal();
        int hotelId = hotelDetails.getHotelId();

        List<HotelFacility> facilityList = hotelService.getFacilitiesByHotelId(hotelId);

        List<FacilityResponseDto> responseList = facilityList.stream().map(facility ->
                        new FacilityResponseDto(
                                facility.getFacilityId(),
                                facility.getFacility(),
                                hotelId))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/hotel/images")
    public ResponseEntity<HotelImageResponseDto> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomHotelDetails hotelDetails = (CustomHotelDetails) authentication.getPrincipal();
        int hotelId = hotelDetails.getHotelId();

        HotelImage savedImage = hotelService.saveHotelImage(hotelId, file);
        HotelImageResponseDto responseDto = new HotelImageResponseDto(savedImage.getImageId(), savedImage.getImageUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);

    }

    @GetMapping("/hotel/viewimages")
    public ResponseEntity<List<HotelImageResponseDto>> getImages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomHotelDetails hotelDetails = (CustomHotelDetails) authentication.getPrincipal();
        int hotelId = hotelDetails.getHotelId();

        List<HotelImage> images = hotelService.getImagesByHotelId(hotelId);

        List<HotelImageResponseDto> dtoList = images.stream()
                .map(img -> new HotelImageResponseDto(img.getImageId(), img.getImageUrl()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/hotel/images/{filename:.+}")
    public ResponseEntity<Resource> serveImage(@PathVariable String filename, HttpServletRequest request) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomHotelDetails hotelDetails = (CustomHotelDetails) authentication.getPrincipal();
        int hotelId = hotelDetails.getHotelId();


        Path filePath = hotelService.getImagePath(hotelId + "/" + filename);

        Resource resource = new UrlResource(filePath.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }
        // Determine file content type
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @GetMapping("/hotel/profile")
    public ResponseEntity<HotelDTO> getHotelProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomHotelDetails hotelDetails = (CustomHotelDetails) authentication.getPrincipal();
        int hotelId = hotelDetails.getHotelId();

        Hotel hotel = hotelService.findById(hotelId);
        HotelDTO dto = hotelService.convertToDto(hotel);
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/hotel/updateprofile")
    public ResponseEntity<HotelDTO> updateHotelProfile(@RequestBody  HotelUpdateDTO updateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomHotelDetails hotelDetails = (CustomHotelDetails) authentication.getPrincipal();
        int hotelId = hotelDetails.getHotelId();

        Hotel updatedHotel = hotelService.updateHotelProfile(hotelId, updateDto);
        HotelDTO dto = hotelService.convertToDto(updatedHotel);
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/hotel/location")
    public ResponseEntity<Void> saveHotelLocation(@RequestBody Map<String, Double> location, Principal principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomHotelDetails hotelDetails = (CustomHotelDetails) authentication.getPrincipal();
        int hotelId = hotelDetails.getHotelId();

        Hotel hotel = hotelService.findById(hotelId);
        hotel.setLatitude(location.get("lat"));
        hotel.setLongitude(location.get("lng"));
        hotel.setDetailsCompleted(true);
        hotelService.savehotel(hotel);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/hotel/bookingList")
    public List<HotelBookingListDTO> hotelBookingList(@AuthenticationPrincipal CustomHotelDetails hotelDetails){
        int hotelId = hotelDetails.getHotelId();
        Hotel hotel = hotelService.findById(hotelId);

        List<Booking> hotelBookings = bookingRepository.findByHotel(hotel);

        List<HotelBookingListDTO> bookingDTOs = hotelBookings.stream().map(b -> {
            HotelBookingListDTO dto = new HotelBookingListDTO();
            dto.setBookingDate(b.getCreatedAt().toString());
            dto.setRoomType(b.getRoomType());
            dto.setNumberOfRooms(String.valueOf(b.getNumberOfRooms()));
            dto.setCheck_in_date(b.getCheckInDate().toString());
            dto.setCheck_out_date(b.getCheckOutDate().toString());
            dto.setAmount(b.getPayment() != null ? b.getPayment().getAmount().toString() : "N/A");
            dto.setPaymentStatus(b.getPayment() != null ? b.getPayment().getPaymentStatus() : "N/A");
            dto.setCustomerName(b.getUser().getName());
            dto.setCustomerPhone(b.getUser().getPhone());
            return dto;
        }).toList();

        return bookingDTOs;

    }

    @GetMapping("/hotel/reviews")
    public List<ReviewDTO> feedbacks(@AuthenticationPrincipal CustomHotelDetails hotelDetails){
        int hotelId = hotelDetails.getHotelId();
        Hotel hotel = hotelService.findById(hotelId);

        List<Review> reviews = reviewService.getReviewsForHotel(hotelId);
        return reviews.stream()
                .map(r -> new ReviewDTO(
                        r.getReviewDate() != null ? r.getReviewDate().toString() : null,
                        r.getComment(),
                        r.getRating())
                ).toList();
    }


    @GetMapping("/hotel/test")
    public String test(){
        return "hotel test endpoint works";
    }

}



