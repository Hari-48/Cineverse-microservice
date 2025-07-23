package com.hari.booking_service.service;

import com.hari.booking_service.model.BookingRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {

    ResponseEntity<?> bookTicket(BookingRequest request);

    ResponseEntity<?> cancelTicket(Long bookingId);

    ResponseEntity<?> updateTicket(Long bookingId);

    ResponseEntity<?> getBookingOfUser(Long userId);

    ResponseEntity<?> getBookingById(Long id);
}
