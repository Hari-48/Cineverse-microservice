package com.hari.movie_service.DTO;

import com.hari.movie_service.entity.Genre;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MovieResponse {
    private Long id;
    private String title;
    private String releaseDate;
    private String language;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String description;
    private GenreResponse genre;

}
