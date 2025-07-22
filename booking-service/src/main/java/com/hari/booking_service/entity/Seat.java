package com.hari.booking_service.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "SEAT")
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SHOW_TIME_ID")
    private Long showTimeId;

    @Column(name = "SEAT_NUMBER")
    private String seatNumber;

    @Column(name = "IS_BOOKED")
    private Boolean isBooked;


}
