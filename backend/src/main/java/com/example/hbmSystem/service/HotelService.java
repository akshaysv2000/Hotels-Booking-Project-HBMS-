package com.example.hbmSystem.service;

import com.example.hbmSystem.models.Hotel;
import com.example.hbmSystem.util.ResponseStructure;

public interface HotelService {
    public ResponseStructure<Hotel> hotelRegister(Hotel hotel);
}
