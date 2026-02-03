package com.example.hbmSystem.repository;

import com.example.hbmSystem.models.Booking;
import com.example.hbmSystem.models.Hotel;
import com.example.hbmSystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {
    List<Booking> findByUser(User user);
    List<Booking> findByHotel(Hotel hotel);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.hotel.hotelId = :hotelId")
    long countByHotelId(@Param("hotelId") int hotelId);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.booking.hotel.hotelId = :hotelId AND p.paymentStatus = 'PAID'")
    Double sumAmountByHotelId(@Param("hotelId") int hotelId);

    @Query("SELECT DATE(b.createdAt), COUNT(b) FROM Booking b WHERE b.hotel.hotelId = :hotelId GROUP BY DATE(b.createdAt)")
    List<Object[]> bookingsPerDay(@Param("hotelId") int hotelId);

    @Query("SELECT DATE(p.paymentDate), SUM(p.amount) FROM Payment p WHERE p.booking.hotel.hotelId = :hotelId AND p.paymentStatus='PAID' GROUP BY DATE(p.paymentDate)")
    List<Object[]> amountPerDay(@Param("hotelId") int hotelId);

    @Query("SELECT b.roomType, COUNT(b) FROM Booking b WHERE b.hotel.hotelId = :hotelId GROUP BY b.roomType")
    List<Object[]> bookingsPerRoomType(@Param("hotelId") int hotelId);

}
