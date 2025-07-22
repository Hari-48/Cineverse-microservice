package com.hari.movie_service.service;

import com.hari.movie_service.entity.Cast;
import com.hari.movie_service.entity.Movie;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CastService {
    ResponseEntity<Movie> addCastToMovie(Long id, List<Cast> cast);

    List<Map<String,Object>> getCast(Long id);

}
