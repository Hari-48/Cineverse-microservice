package com.hari.movie_service.service;

import com.hari.movie_service.entity.Movie;
import com.hari.movie_service.entity.Poster;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface PosterService {
    ResponseEntity<Movie> savePoster(Long movieId, Poster poster);

    List<Map<String, Object>> getPoster(Long id);


    ResponseEntity<Poster> updatePosterActive(Long posterId);
}
