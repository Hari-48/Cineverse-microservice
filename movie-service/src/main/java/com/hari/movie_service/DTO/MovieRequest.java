package com.hari.movie_service.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class MovieRequest {
    private Long id;
    private String title;
    private String description;
    private String releaseDate;
    private String language;
    private String status;
    private Long genreId;
}
