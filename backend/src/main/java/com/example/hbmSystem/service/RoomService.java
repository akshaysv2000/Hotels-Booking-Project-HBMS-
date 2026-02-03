package com.example.hbmSystem.service;

import com.example.hbmSystem.dto.RoomDto;
import com.example.hbmSystem.exception.ResourceNotFoundException;
import com.example.hbmSystem.models.Hotel;
import com.example.hbmSystem.models.Room;
import com.example.hbmSystem.repository.HotelRepository;
import com.example.hbmSystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;

    public Room addRoomToHotel(int hotelId, RoomDto roomDto){
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Hotel Not Found"));
        Room room=new Room();
        room.setHotel(hotel);
        room.setRoomType(roomDto.getRoomType());
        room.setPricePerNight(roomDto.getPricePerNight());
        room.setTotalRooms(roomDto.getTotalRooms());
        return roomRepository.save(room);
    }



}
