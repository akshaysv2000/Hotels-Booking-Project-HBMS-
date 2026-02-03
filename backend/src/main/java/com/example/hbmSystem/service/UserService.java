package com.example.hbmSystem.service;

import com.example.hbmSystem.models.User;
import com.example.hbmSystem.util.ResponseStructure;

public interface UserService {
    public ResponseStructure<User> userRegister(User user);

}
