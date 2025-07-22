package com.hari.booking_service.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
@Data
public class BookingRequest {
    private Long userId;
    private Long movieId;
    private Long showTimeId;
    private String seatNumbers;


}
