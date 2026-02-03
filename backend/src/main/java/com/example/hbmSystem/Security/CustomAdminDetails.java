package com.example.hbmSystem.Security;



import com.example.hbmSystem.models.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class CustomAdminDetails implements UserDetails {

    private final Admin admin;

    public CustomAdminDetails(Admin admin) {
        this.admin = admin;
    }

    public int getAdminId() {
        return admin.getAdminId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
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
        return true; // customize if needed for admin
    }
}

