package com.hari.movie_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hari.movie_service.DTO.MovieSession;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SHOW_TIME")
@Data
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="ID")
    private Long id ;
    @Column(name = "THEATRE_NAME")
    private String theatreName;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "TIME")
    private LocalTime time;

    @Column(name = "SHOW_NAME")
    @Enumerated(EnumType.STRING)
    private MovieSession session;

    @Column(name = "LANGUAGE")
    private String language ;

    @ManyToOne
    @JoinColumn(name = "MOVIE_ID")
    @JsonIgnore
    private Movie movie;


}
