package com.example.hbmSystem.service;

import com.example.hbmSystem.dto.BookingDTO;
import com.example.hbmSystem.dto.PaymentDTO;
import com.example.hbmSystem.exception.ResourceNotFoundException;
import com.example.hbmSystem.models.Booking;
import com.example.hbmSystem.models.Hotel;
import com.example.hbmSystem.models.Room;
import com.example.hbmSystem.models.User;
import com.example.hbmSystem.repository.BookingRepository;
import com.example.hbmSystem.repository.HotelRepository;
import com.example.hbmSystem.repository.RoomRepository;
import com.example.hbmSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class BookingService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public boolean checkRoomAvailability(int roomId, LocalDate checkIn, LocalDate checkOut, int numberOfRooms) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not Found"));

        long bookedRooms = room.getBooking().stream()
                .filter(b -> b.getStatus() == Booking.Status.Booked &&
                        checkIn.isBefore(b.getCheckOutDate()) &&
                        b.getCheckInDate().isBefore(checkOut))
                .mapToLong(Booking::getNumberOfRooms) // sum number of rooms booked
                .sum();
        int availableRooms = room.getTotalRooms() - (int) bookedRooms;

        return availableRooms >= numberOfRooms;
    }


    public Booking createBooking(int userId, int hotelId, int roomId, LocalDate checkIn, LocalDate checkOut, int numberOfRooms) {
        if (!checkIn.isBefore(checkOut)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        if (numberOfRooms <= 0) {
            throw new IllegalArgumentException("Number of rooms must be at least 1");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        // Check if enough rooms are available for the requested quantity
        long activeBookings = room.getBooking().stream()
                .filter(b -> b.getStatus() == Booking.Status.Booked &&
                        checkIn.isBefore(b.getCheckOutDate()) &&
                        b.getCheckInDate().isBefore(checkOut))
                .mapToLong(Booking::getNumberOfRooms)
                .sum();

        int availableRooms = room.getTotalRooms() - (int) activeBookings;
        if (availableRooms < numberOfRooms) {
            throw new IllegalStateException("Not enough rooms available for selected dates");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setHotel(hotel);
        booking.setRoom(room);
        booking.setNumberOfRooms(numberOfRooms);
        booking.setRoomType(room.getRoomType());
        booking.setCheckInDate(checkIn);
        booking.setCheckOutDate(checkOut);
        booking.setStatus(Booking.Status.Booked);

        return bookingRepository.save(booking);
    }


    public BigDecimal getPricePerNight(int roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        return room.getPricePerNight();
    }

    public BigDecimal calculateTotalPrice(int roomId, LocalDate checkIn, LocalDate checkOut, int numberOfRooms) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (nights <= 0) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        if (numberOfRooms <= 0) {
            throw new IllegalArgumentException("Number of rooms must be at least 1");
        }

        return room.getPricePerNight()
                .multiply(BigDecimal.valueOf(nights))
                .multiply(BigDecimal.valueOf(numberOfRooms));
    }


    public BookingDTO convertToDto(Booking booking) {
        BookingDTO dto = new BookingDTO();

        dto.setBookingId(booking.getBookingId());

        dto.setUserId(booking.getUser().getUserId());
        dto.setUsername(booking.getUser().getUsername());

        dto.setHotelId(booking.getHotel().getHotelId());
        dto.setHotelName(booking.getHotel().getName());

        dto.setRoomId(booking.getRoom().getRoomId());
        dto.setRoomType(booking.getRoom().getRoomType());
        dto.setPricePerNight(booking.getRoom().getPricePerNight());
        dto.setNumberOfRooms(booking.getNumberOfRooms());

        dto.setCheckInDate(booking.getCheckInDate());
        dto.setCheckOutDate(booking.getCheckOutDate());

        dto.setStatus(booking.getStatus().name());
        dto.setCreatedAt(booking.getCreatedAt());

        if (booking.getPayment() != null) {
            PaymentDTO paymentDto = new PaymentDTO();
            paymentDto.setPaymentId(booking.getPayment().getPaymentId());
            paymentDto.setAmount(booking.getPayment().getAmount());
            paymentDto.setPaymentDate(booking.getPayment().getPaymentDate());
            paymentDto.setPaymentMethod(booking.getPayment().getPaymentMethod());
            paymentDto.setPaymentStatus(booking.getPayment().getPaymentStatus());
            dto.setPayment(paymentDto);

        }

        return dto;
    }

}
