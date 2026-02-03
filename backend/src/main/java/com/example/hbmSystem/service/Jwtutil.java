package com.example.hbmSystem.service;

import com.example.hbmSystem.Security.CustomAdminDetails;
import com.example.hbmSystem.Security.CustomHotelDetails;
import com.example.hbmSystem.Security.CustomUserDetails;
import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class Jwtutil {
    private final String SECRET_KEY="your_very_long_secret_key_at_least_32_chars";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails){
        JwtBuilder builder = Jwts.builder();
        builder.subject(userDetails.getUsername());

        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        builder.claim("role",role);

        if (userDetails instanceof CustomHotelDetails){
            int hotelId = ((CustomHotelDetails) userDetails).getHotelId();
            builder.claim("hotelId", hotelId);
        } else if (userDetails instanceof CustomUserDetails) {
            int userId = ((CustomUserDetails) userDetails).getUserId();
        } else if (userDetails instanceof CustomAdminDetails) {
            int adminId = ((CustomAdminDetails) userDetails).getAdminId();
        }

        builder.issuedAt(new Date());

        Date expirationDate=new Date(System.currentTimeMillis()+1000*60*60*10);
        builder.expiration(expirationDate);

        builder.signWith(getSigningKey());

        String token = builder.compact();
        return token;

    }

    public String extractUserName(String token){
        JwtParser parser = Jwts.parser().setSigningKey(getSigningKey()).build();
        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        String username = claims.getSubject();
        return username;
    }

    public Integer extractHotelId(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(getSigningKey()).build();
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.get("hotelId", Integer.class);
    }
    public Integer extractUserId(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(getSigningKey()).build();
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.get("userId", Integer.class);
    }
    public Integer extractAdminId(String token) {
        JwtParser parser = Jwts.parser().setSigningKey(getSigningKey()).build();
        Claims claims = parser.parseClaimsJws(token).getBody();
        return claims.get("adminId", Integer.class);
    }


    public boolean validateToken(String token,UserDetails userDetails){
        String usernameFromToken = extractUserName(token);
        boolean isExpired=isTokenExpired(token);
        return usernameFromToken.equals(userDetails.getUsername())&& !isExpired;
    }

    public boolean isTokenExpired(String token){
        JwtParser parser = Jwts.parser().setSigningKey(getSigningKey()).build();
        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Date expirationDate = claims.getExpiration();
        return expirationDate.before(new Date());
    }

}
