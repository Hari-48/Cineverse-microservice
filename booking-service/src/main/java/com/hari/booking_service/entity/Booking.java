package com.hari.booking_service.entity;

import com.hari.booking_service.model.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Table(name = "BOOKING")
@Data
public class Booking {

    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "MOVIE_ID")
    private Long movieId;

    @Column(name = "SHOW_TIME_ID")
    private Long showTimeId;

    @Column(name = "SEAT_NUMBERS")
    private String seatNumbers;

    @Column(name = "BOOKING_TIME")
    private LocalTime bookingTime;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "QR_CODE_URL", length = 1024)
    private String qrCodeUrl;

}
