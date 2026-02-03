package com.example.hbmSystem.service;

import com.example.hbmSystem.Security.CustomAdminDetails;
import com.example.hbmSystem.models.Admin;
import com.example.hbmSystem.repository.AdminRepository;
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
import java.util.Optional;

@Service
public class AdminService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    ResponseStructure<Admin> res;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Admin not Found"));
        return new CustomAdminDetails(admin);
    }

    public ResponseStructure<Admin> registerAdmin(Admin admin){
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        Admin save = adminRepository.save(admin);
        res.setStatus(HttpStatus.OK.name());
        res.setMessage("Inserted");
        res.setData(save);
        return res;
    }

    public Optional<Admin> findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
