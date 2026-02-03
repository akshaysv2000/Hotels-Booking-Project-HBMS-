package com.example.hbmSystem.Security;



import com.example.hbmSystem.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public int getUserId() {
        return user.getUserId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
        return true; // customize if you have an enabled field or status check for users
    }
}

