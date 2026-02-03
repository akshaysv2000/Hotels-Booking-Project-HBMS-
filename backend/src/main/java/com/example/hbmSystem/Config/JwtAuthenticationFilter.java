package com.example.hbmSystem.Config;

import com.example.hbmSystem.service.AdminService;
import com.example.hbmSystem.service.HotelServiceImplementation;
import com.example.hbmSystem.service.Jwtutil;
import com.example.hbmSystem.service.UserServiceImplementation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {




    @Autowired
    private  Jwtutil jwtutil;
    @Autowired
    private UserServiceImplementation userServiceImplementation;
    @Autowired
    private HotelServiceImplementation hotelServiceImplementation;
    @Autowired
    private AdminService adminService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("JWT Filter triggered for path: " + request.getRequestURI());
        String authHeader = request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = authHeader.substring(7);
        String username = jwtutil.extractUserName(token);

        if (username != null){
            if (SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails=null;

                String path = request.getRequestURI();
                boolean isUserPath = path.startsWith("/user/");
                boolean isHotelPath = path.startsWith("/hotel/");
                boolean isAdminPath = path.startsWith("/admin/");

                if (isUserPath){
                     userDetails = userServiceImplementation.loadUserByUsername(username);
                }
                if (isHotelPath){
                    userDetails = hotelServiceImplementation.loadUserByUsername(username);
                }
                if (isAdminPath){
                    userDetails = adminService.loadUserByUsername(username);
                }
                boolean isValidToken = false;
                if (userDetails != null){
                    isValidToken =jwtutil.validateToken(token, userDetails);
                }
                if (isValidToken){
                    UsernamePasswordAuthenticationToken authToken;
                    authToken =new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    WebAuthenticationDetailsSource detailsSource = new WebAuthenticationDetailsSource();
                    authToken.setDetails(detailsSource.buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request,response);

    }
}
