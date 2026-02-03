package com.example.hbmSystem.controllers;

import com.example.hbmSystem.dto.HotelCardSearchResultDTO;
import com.example.hbmSystem.service.HotelServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/user")
public class HotelSearchController {

    @Autowired
    private HotelServiceImplementation hotelService;


    @GetMapping("/hotels/search")
    public ResponseEntity<HotelCardSearchResultDTO> searchHotels(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude) {

        HotelCardSearchResultDTO results = hotelService.combinedHotelCardSearch(name, location, latitude, longitude);
        return ResponseEntity.ok(results);
    }


    @GetMapping("/hotel-images/{hotelId}/{filename:.+}")
    public ResponseEntity<Resource> serveHotelImage(
            @PathVariable int hotelId,
            @PathVariable String filename,
            HttpServletRequest request) throws IOException {

        Path filePath = hotelService.getImagePath(hotelId + "/" + filename);
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable()) {
            return ResponseEntity.notFound().build();
        }
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

}

