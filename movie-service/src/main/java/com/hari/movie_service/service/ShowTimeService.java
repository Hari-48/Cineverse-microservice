package com.hari.movie_service.service;

import com.hari.movie_service.entity.Movie;
import com.hari.movie_service.entity.ShowTime;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ShowTimeService {
    ResponseEntity<Movie> addShowTime(Long movieId, ShowTime showTime);

    List<Map<String, Object>> getShowTimingByMovieId(Long movieId);
}
