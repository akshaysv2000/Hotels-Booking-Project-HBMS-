package com.example.hbmSystem.Config;

import com.example.hbmSystem.service.AdminService;
import com.example.hbmSystem.service.HotelServiceImplementation;
import com.example.hbmSystem.service.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf(customizer ->customizer.disable());
        http.cors(Customizer.withDefaults());
        http.authorizeHttpRequests(request -> request.requestMatchers("/user/login", "/hotel/login", "/admin/login").permitAll()
                .requestMatchers("/userRegistration", "/hotelRegistration", "/adminRegistration").permitAll()
                .requestMatchers("/user/hotel-images/**").permitAll()
                .requestMatchers("/user/**").hasRole("USER")
                .requestMatchers("/hotel/**").hasRole("HOTEL")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated());
        http.sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider userAuthProvider(UserServiceImplementation userService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider hotelAuthProvider(HotelServiceImplementation hotelService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(hotelService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public DaoAuthenticationProvider adminAuthProvider(AdminService adminService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(adminService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean(name = "userAuthenticationManager")
    @Primary
    public AuthenticationManager userAuthenticationManager(DaoAuthenticationProvider userAuthProvider) {
        return new ProviderManager(userAuthProvider);
    }

    @Bean(name = "hotelAuthenticationManager")
    public AuthenticationManager hotelAuthenticationManager(DaoAuthenticationProvider hotelAuthProvider) {
        return new ProviderManager(hotelAuthProvider);
    }

    @Bean(name = "adminAuthenticationManager")
    public AuthenticationManager adminAuthenticationManager(DaoAuthenticationProvider adminAuthProvider) {
        return new ProviderManager(adminAuthProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
