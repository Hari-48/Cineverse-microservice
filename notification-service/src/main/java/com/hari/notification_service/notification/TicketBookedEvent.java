package com.hari.notification_service.notification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketBookedEvent {
    private Long bookingId;
    private Long userId;
    private Long showTimeId;
    private List<String> seatNumbers;
    private LocalTime bookingTime;
    private String qr;  // ✅ Add this to handle incoming JSON
}
