package com.hari.movie_service.DTO;

import com.hari.movie_service.entity.Movie;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ShowTimingRequest {

    private String theatreName;
    private LocalDate date;
    private MovieSession session;
    private String language ;
    private Long movieId;

}
