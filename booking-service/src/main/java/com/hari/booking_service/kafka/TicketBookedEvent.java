package com.hari.booking_service.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@AllArgsConstructor
@Data
public class TicketBookedEvent {
    private Long bookingId;
    private Long userId;
    private Long showTimeId;
    private List<String> seatNumbers;
    private LocalTime bookingTime;
    private String qr;
}
