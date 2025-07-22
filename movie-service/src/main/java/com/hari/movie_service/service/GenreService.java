package com.hari.movie_service.service;

import com.hari.movie_service.DTO.GenreResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenreService {
    ResponseEntity<List<GenreResponse>> getAllGenre();

    ResponseEntity<GenreResponse> saveGenre(String genreName);
}
