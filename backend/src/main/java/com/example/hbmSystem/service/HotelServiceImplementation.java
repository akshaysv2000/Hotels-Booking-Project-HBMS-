package com.example.hbmSystem.service;

import com.example.hbmSystem.Security.CustomHotelDetails;
import com.example.hbmSystem.dto.*;
import com.example.hbmSystem.exception.ResourceNotFoundException;
import com.example.hbmSystem.models.*;
import com.example.hbmSystem.repository.DescriptionRepository;
import com.example.hbmSystem.repository.HotelFacilityRepository;
import com.example.hbmSystem.repository.HotelImageRepository;
import com.example.hbmSystem.repository.HotelRepository;
import com.example.hbmSystem.util.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelServiceImplementation implements HotelService, UserDetailsService {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    ResponseStructure<Hotel> res;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DescriptionRepository descriptionRepository;
    @Autowired
    private HotelFacilityRepository hotelFacilityRepository;
    @Autowired
    private HotelImageRepository hotelImageRepository;
    @Autowired
    private RoomService roomService;

    @Override
    public ResponseStructure<Hotel> hotelRegister(Hotel hotel) {
        ResponseStructure<Hotel> res = new ResponseStructure<>();
        Optional<Hotel> existingHotel = hotelRepository.findByUsername(hotel.getUsername());

        if (existingHotel.isPresent()) {
            res.setStatus(HttpStatus.CONFLICT.name());
            res.setMessage("Username already exists");
            res.setData(null);
            return res;
        }
        hotel.setPassword(passwordEncoder.encode(hotel.getPassword()));
        Hotel save = hotelRepository.save(hotel);
        res.setStatus(HttpStatus.OK.name());
        res.setMessage("Inserted");
        res.setData(save);
        return res;
    }

    public Hotel savehotel(Hotel hotel){
        return hotelRepository.save(hotel);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Hotel hotel = hotelRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Hotel not found: " + username));
        if (hotel.getStatus() != Hotel.Status.Approved) {
            throw new UsernameNotFoundException("Hotel is not approved or has been rejected.");
        }
        return new CustomHotelDetails(hotel);
    }

    public HotelDescription addHotelDescription(int hotelId, HotelDescription hotelDescription){
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel Not Found"));
        String hotelname = hotel.getName();
        hotelDescription.setHotelname(hotelname);
        hotelDescription.setHotel(hotel);
        HotelDescription save = descriptionRepository.save(hotelDescription);
        return save;

    }

    public HotelFacility addFacilityToHotel(int hotelId, FacilityInputDTO facilityInputDTO){
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        HotelFacility hotelFacility=new HotelFacility();
        hotelFacility.setFacility(facilityInputDTO.getFacility());
        hotelFacility.setHotel(hotel);

        return hotelFacilityRepository.save(hotelFacility);
    }


    //incase need in future
    public List<HotelFacility> getFacilitiesByHotelId(int hotelId) {
        return hotelFacilityRepository.findByHotel_HotelId(hotelId);
    }

    private final String uploadDir = "C:\\Users\\AKSHAY\\OneDrive\\Desktop\\hotelimages";

    public HotelImage saveHotelImage(int hotelId, MultipartFile file) throws IOException {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
        Path hotelDir = Paths.get(uploadDir, String.valueOf(hotelId));
        if (!Files.exists(hotelDir)) {
            Files.createDirectories(hotelDir);
        }
        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFileName = UUID.randomUUID().toString() + fileExtension;

        Path targetLocation = hotelDir.resolve(newFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        // Store relative path or URL in DB
        String filePath = hotelId + "/" + newFileName; // relative path, e.g., "1/uuid.jpg"

        HotelImage hotelImage = new HotelImage();
        hotelImage.setHotel(hotel);
        hotelImage.setImageUrl(filePath);

        return hotelImageRepository.save(hotelImage);

    }

    public Path getImagePath(String relativeFilePath) {
        return Paths.get(uploadDir).resolve(relativeFilePath).toAbsolutePath().normalize();
    }

    public List<HotelImage> getImagesByHotelId(int hotelId) {
        return hotelImageRepository.findByHotel_HotelId(hotelId);
    }

    //search

    public List<HotelDTO> searchHotelsByName(String name) {
        List<Hotel> hotels = hotelRepository.findByNameContainingIgnoreCaseAndStatus(name, Hotel.Status.Approved);
        return hotels.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public HotelDTO convertToDto(Hotel hotel) {
        HotelDTO dto = new HotelDTO();
        dto.setHotelId(hotel.getHotelId());
        dto.setName(hotel.getName());
        dto.setAddress(hotel.getAddress());
        dto.setOwnerName(hotel.getOwnerName());
        dto.setContactNumber(hotel.getContactNumber());
        dto.setStatus(hotel.getStatus().name());
        dto.setCreatedAt(hotel.getCreatedAt());
        dto.setLocation(hotel.getLocation());
        return dto;
    }

    public Hotel updateHotelProfile(int hotelId, HotelUpdateDTO updateDto) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));

        if (updateDto.getUsername() != null && !updateDto.getUsername().isBlank()) {
            boolean usernameExists = hotelRepository.existsByUsername(updateDto.getUsername());
            if (usernameExists && !updateDto.getUsername().equals(hotel.getUsername())) {
                throw new IllegalArgumentException("Username already taken");
            }
            hotel.setUsername(updateDto.getUsername());
        }

        if (updateDto.getContactNumber() != null && !updateDto.getContactNumber().isBlank()) {
            hotel.setContactNumber(updateDto.getContactNumber());
        }

        if (updateDto.getPassword() != null && !updateDto.getPassword().isBlank()) {
            hotel.setPassword(passwordEncoder.encode(updateDto.getPassword()));
        }
        return hotelRepository.save(hotel);
    }




    public Optional<Hotel> findByUsername(String username) {
        return hotelRepository.findByUsername(username);
    }


    public Hotel findById(int hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
    }


    public HotelCardSearchResultDTO combinedHotelCardSearch(String name, String location, Double latitude, Double longitude) {
        List<HotelCardDTO> byName = new ArrayList<>();
        List<HotelCardDTO> byLocation = new ArrayList<>();

        if (name != null && !name.isBlank()) {
            byName = hotelRepository.findByNameContainingIgnoreCaseAndStatus(name, Hotel.Status.Approved)
                    .stream()
                    .map(this::convertToHotelCardDTO)
                    .collect(Collectors.toList());
        }

        if ((location != null && !location.isBlank()) || (latitude != null && longitude != null)) {
            if (latitude != null && longitude != null) {
                byLocation = hotelRepository.findWithinRadius(latitude, longitude, 20.0)
                        .stream()
                        .map(this::convertToHotelCardDTO)
                        .collect(Collectors.toList());
            } else if (location != null && !location.isBlank()) {
                byLocation = hotelRepository.findByLocationContainingIgnoreCaseAndStatus(location, Hotel.Status.Approved)
                        .stream()
                        .map(this::convertToHotelCardDTO)
                        .collect(Collectors.toList());
            }
        }

        Set<Integer> byNameIds = byName.stream()
                .map(HotelCardDTO::getHotelId)
                .collect(Collectors.toSet());
        byLocation = byLocation.stream()
                .filter(dto -> !byNameIds.contains(dto.getHotelId()))
                .collect(Collectors.toList());

        return new HotelCardSearchResultDTO(byLocation, byName);
    }


    public HotelCardDTO convertToHotelCardDTO(Hotel hotel) {
        HotelCardDTO dto = new HotelCardDTO();
        dto.setHotelId(hotel.getHotelId());
        dto.setName(hotel.getName());
        dto.setLocation(hotel.getLocation());


        String description = "";
        if (hotel.getHotelDescription() != null) {
            description = hotel.getHotelDescription().getDescription();
        }
        dto.setDescription(description);


        List<HotelImage> images = hotelImageRepository.findByHotel_HotelId(hotel.getHotelId());
        String imageUrl = null;
        if (!images.isEmpty()) {
            String storedPath = images.get(0).getImageUrl();
            String filenameOnly = storedPath.contains("/") ? storedPath.substring(storedPath.indexOf("/") + 1) : storedPath;
            imageUrl = "http://localhost:8080/user/hotel-images/" + hotel.getHotelId() + "/" + filenameOnly;
        }
        dto.setImageUrl(imageUrl);


        List<Room> rooms = hotel.getRooms();
        BigDecimal minPrice = null;
        if (rooms != null && !rooms.isEmpty()) {
            minPrice = rooms.stream()
                    .map(Room::getPricePerNight)
                    .filter(Objects::nonNull)
                    .min(Comparator.naturalOrder())
                    .orElse(null);
        }
        dto.setStartingPrice(minPrice);

        return dto;
    }






}
