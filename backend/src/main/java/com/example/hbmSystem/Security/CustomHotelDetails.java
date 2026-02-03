package com.example.hbmSystem.Security;


import com.example.hbmSystem.models.Hotel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomHotelDetails implements UserDetails {

    private final Hotel hotel;

    public CustomHotelDetails(Hotel hotel) {
        this.hotel = hotel;
    }

    public int getHotelId() {
        return hotel.getHotelId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assume one role per hotel; adapt if multiple roles
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_HOTEL"));
    }

    @Override
    public String getPassword() {
        return hotel.getPassword();
    }

    @Override
    public String getUsername() {
        return hotel.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // customize as needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // customize as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // customize as needed
    }

    @Override
    public boolean isEnabled() {
        // For example, consider hotel status
        return true;
//        return hotel.getStatus() == Hotel.Status.Approved;
    }
}
