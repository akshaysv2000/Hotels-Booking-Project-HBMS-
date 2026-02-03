package com.example.hbmSystem.service;

import com.example.hbmSystem.Security.CustomUserDetails;
import com.example.hbmSystem.dto.UserResponseDTO;
import com.example.hbmSystem.dto.UserUpdateDTO;
import com.example.hbmSystem.exception.ResourceNotFoundException;
import com.example.hbmSystem.models.User;
import com.example.hbmSystem.repository.UserRepository;
import com.example.hbmSystem.util.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImplementation implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    ResponseStructure<User> res;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ResponseStructure<User> userRegister(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            res.setStatus(HttpStatus.BAD_REQUEST.name());
            res.setMessage("Username already taken");
            res.setData(null);
            return res;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User save = userRepository.save(user);
        res.setStatus(HttpStatus.OK.name());
        res.setMessage("Inserted");
        res.setData(save);
        return res;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
       return new CustomUserDetails(user);

    }

    public User updateUserProfile(int userId, UserUpdateDTO updateDto) {
        User user = findById(userId);

        if (updateDto.getUsername() != null && !updateDto.getUsername().isBlank()) {
            if (!updateDto.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(updateDto.getUsername())) {
                throw new IllegalArgumentException("Username already taken");
            }
            user.setUsername(updateDto.getUsername());
        }

        if (updateDto.getName() != null && !updateDto.getName().isBlank()) {
            user.setName(updateDto.getName());
        }

        if (updateDto.getEmail() != null && !updateDto.getEmail().isBlank()) {
            user.setEmail(updateDto.getEmail());
        }

        if (updateDto.getPhone() != null && !updateDto.getPhone().isBlank()) {
            user.setPhone(updateDto.getPhone());
        }

        if (updateDto.getPassword() != null && !updateDto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updateDto.getPassword()));
        }

        return userRepository.save(user);
    }

    public UserResponseDTO convertToResponseDto(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

    public User findById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }


}
