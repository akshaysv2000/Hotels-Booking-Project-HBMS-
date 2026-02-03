package com.example.hbmSystem.controllers;

import com.example.hbmSystem.Security.CustomAdminDetails;
import com.example.hbmSystem.dto.*;
import com.example.hbmSystem.exception.ResourceNotFoundException;
import com.example.hbmSystem.models.Admin;
import com.example.hbmSystem.models.Hotel;
import com.example.hbmSystem.models.User;
import com.example.hbmSystem.repository.AdminRepository;
import com.example.hbmSystem.repository.HotelRepository;
import com.example.hbmSystem.repository.UserRepository;
import com.example.hbmSystem.service.AdminService;
import com.example.hbmSystem.service.Jwtutil;
import com.example.hbmSystem.util.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AdminController {
    @Autowired
    public AdminService adminService;
    @Autowired
    ResponseStructure<Admin> response;
    @Autowired
    private Jwtutil jwtutil;
    @Autowired
    @Qualifier("adminAuthenticationManager")
    public AuthenticationManager authenticationManager;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/adminRegistration")
    public ResponseEntity<ResponseStructure<Admin>> adminReg(@RequestBody Admin newadmin){
        ResponseStructure<Admin> save = adminService.registerAdmin(newadmin);
        return new ResponseEntity<ResponseStructure<Admin>>(save, HttpStatus.OK);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AuthResponse> adminLogin(@RequestBody LoginRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails admin = adminService.loadUserByUsername(request.getUsername());
        String token = jwtutil.generateToken(admin);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PatchMapping("/admin/photel/{hotelId}/approve")
    public ResponseEntity<?> approveHotel(@PathVariable int hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
        hotel.setStatus(Hotel.Status.Approved);
        hotelRepository.save(hotel);
        return ResponseEntity.ok(Collections.singletonMap("message", "Hotel approved successfully"));
    }

    @PatchMapping("/admin/photel/{hotelId}/reject")
    public ResponseEntity<?> rejectHotel(@PathVariable int hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
        hotel.setStatus(Hotel.Status.Rejected);
        hotelRepository.save(hotel);
        return ResponseEntity.ok(Collections.singletonMap("message", "Hotel rejected successfully"));
    }

    @GetMapping("/admin/hotels/pending")
    public ResponseEntity<List<HotelDTO>> listPendingHotels() {
        List<Hotel> pendingHotels = hotelRepository.findByStatus(Hotel.Status.Pending);
        List<HotelDTO> result = pendingHotels.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    private HotelDTO toDto(Hotel hotel) {
        HotelDTO dto = new HotelDTO();
        dto.setHotelId(hotel.getHotelId());

        dto.setName(hotel.getName());
        dto.setAddress(hotel.getAddress());
        dto.setOwnerName(hotel.getOwnerName());
        dto.setContactNumber(hotel.getContactNumber());
        dto.setStatus(hotel.getStatus().name());
        dto.setCreatedAt(hotel.getCreatedAt());
        return dto;
    }

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDTO>> listAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> result = users.stream().map(this::userToDto).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    private UserDTO userToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        return dto;
    }

    @GetMapping("/admin/hotels/approved")
    public ResponseEntity<List<HotelDTO>> listApprovedHotels() {
        List<Hotel> approvedHotels = hotelRepository.findByStatus(Hotel.Status.Approved);
        List<HotelDTO> result = approvedHotels.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/admin/profile")
    public ResponseEntity<Admin> getAdminProfile(@AuthenticationPrincipal CustomAdminDetails adminDetails){
        String username = adminDetails.getUsername();
        Admin admin = adminService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));
        return ResponseEntity.ok(admin);

    }

    @PatchMapping("/admin/profileEdit")
    public ResponseEntity<Admin> editAdminProfile(
            @AuthenticationPrincipal CustomAdminDetails adminDetails,
            @RequestBody AdminEditRequest editRequest) {

        String username = adminDetails.getUsername();
        Admin admin = adminService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found"));


        if (editRequest.getNewUsername() != null && !editRequest.getNewUsername().trim().isEmpty()) {
            String newUsername = editRequest.getNewUsername();
            if (adminRepository.findByUsername(newUsername).isPresent()) {
                throw new IllegalArgumentException("Username already exists");
            }
            admin.setUsername(newUsername);
        }

        if (editRequest.getNewPassword() != null && !editRequest.getNewPassword().trim().isEmpty()) {
             admin.setPassword(passwordEncoder.encode(editRequest.getNewPassword()));
        }

        adminRepository.save(admin);
        return ResponseEntity.ok(admin);
    }


    @GetMapping("/admin/test")
    public String test(){
        return "admin test endpoint works well";
    }


}
