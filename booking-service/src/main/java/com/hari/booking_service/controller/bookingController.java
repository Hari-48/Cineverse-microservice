package com.hari.booking_service.controller;

import com.hari.booking_service.model.BookingRequest;
import com.hari.booking_service.service.BookingService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class bookingController {

    private final BookingService bookingService;

//
//    POST /bookings – Book ticket
//
//    PUT /bookings/{id}/cancel – Cancel a booking
//
//    PUT /bookings/{id}/reschedule – Reschedule a booking
//
//    GET /bookings/user/{userId} – Get all bookings for a user
//
//    GET /bookings/{id} – Get booking by ID


    @PostMapping("/")
    public ResponseEntity<?> bookTicket(@RequestBody BookingRequest request){
        return bookingService.bookTicket(request);

    }

    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<?> cancelTicket(@PathVariable Long bookingId){
        return bookingService.cancelTicket(bookingId);
    }

    @PutMapping("/{bookingId}/reschedule")
    public ResponseEntity<?> updateTicket(@PathVariable Long bookingId){
        return bookingService.updateTicket(bookingId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBookingsOfUser(@PathVariable Long userId){
        return bookingService.getBookingOfUser (userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id);

    }

    @GetMapping("/qr/{id}")
    public ResponseEntity<Resource> downloadQr(@PathVariable Long id) throws IOException {
        String filePath = "src/main/resources/qr/booking-" + id + ".png";
        Path path = Paths.get(filePath);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(resource);
    }


}
