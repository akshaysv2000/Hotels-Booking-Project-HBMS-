package com.example.hbmSystem.controllers;

import com.example.hbmSystem.Security.CustomUserDetails;
import com.example.hbmSystem.dto.*;
import com.example.hbmSystem.models.Booking;
import com.example.hbmSystem.models.User;
import com.example.hbmSystem.repository.BookingRepository;
import com.example.hbmSystem.service.Jwtutil;
import com.example.hbmSystem.service.UserServiceImplementation;
import com.example.hbmSystem.util.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {

    @Autowired
    @Qualifier("userAuthenticationManager")
    public AuthenticationManager authenticationManager;
    @Autowired
    private UserServiceImplementation userService;
    @Autowired
    ResponseStructure<User> response;
    @Autowired
    private Jwtutil jwtutil;
    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/userRegistration")
    public ResponseEntity<ResponseStructure<User>> userReg(@RequestBody User newUser){
        ResponseStructure<User> save = userService.userRegister(newUser);
        if ("BAD_REQUEST".equals(save.getStatus())) {
            return new ResponseEntity<>(save, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(save, HttpStatus.OK);

    }

    @PostMapping("/user/login")
    public ResponseEntity<AuthResponse> userLogin(@RequestBody LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userService.loadUserByUsername(request.getUsername());
        String token = jwtutil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));

    }

    @GetMapping("/user/profile")
    public ResponseEntity<UserResponseDTO> getUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        int userId = userDetails.getUserId();
        User user = userService.findById(userId);
        UserResponseDTO responseDto = userService.convertToResponseDto(user);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/user/profileUpdate")
    public ResponseEntity<UserResponseDTO> updateUserProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody UserUpdateDTO updateDto) {

        int userId = userDetails.getUserId();
        User updatedUser = userService.updateUserProfile(userId, updateDto);
        UserResponseDTO responseDto = userService.convertToResponseDto(updatedUser);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("user/bookingList")
    public List<UserBookingListDTO> userBookingList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        int userId = userDetails.getUserId();
        User user = userService.findById(userId);
        List<Booking> userBookings = bookingRepository.findByUser(user);

        List<UserBookingListDTO> bookingDTOs = userBookings.stream().map(b -> {
            UserBookingListDTO dto = new UserBookingListDTO();
            dto.setBookingDate(b.getCreatedAt().toString());
            dto.setHotelName(b.getHotel().getName());
            dto.setRoomType(b.getRoomType());
            dto.setNumberOfRooms(String.valueOf(b.getNumberOfRooms()));
            dto.setCheck_in_date(b.getCheckInDate().toString());
            dto.setCheck_out_date(b.getCheckOutDate().toString());
            dto.setAmount(b.getPayment() != null ? b.getPayment().getAmount().toString() : "N/A");
            dto.setPaymentStatus(b.getPayment() != null ? b.getPayment().getPaymentStatus() : "N/A");
            dto.setHotelContactNumber(b.getHotel().getContactNumber());
            return dto;
        }).toList();

        return bookingDTOs;
    }





    @GetMapping("/user/test")
    public String test(){
        return "user test endpoint works";
    }




}
