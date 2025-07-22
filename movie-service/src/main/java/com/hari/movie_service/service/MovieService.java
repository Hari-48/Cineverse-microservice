package com.hari.movie_service.service;

import com.hari.movie_service.DTO.MovieRequest;
import com.hari.movie_service.DTO.MovieResponse;
import com.hari.movie_service.entity.Movie;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;


public interface MovieService {
    ResponseEntity<List<MovieResponse>> getAllMovies();

    ResponseEntity<Movie> saveMovie(MovieRequest movieResponse);

    ResponseEntity<List<Map<String,Object>>> getAllMoviesWithGenre();

    ResponseEntity<List<Map<String, Object>>> getAll();

    ResponseEntity <List<Map<String,Object>>> getMovieById(Long id);

    ResponseEntity<?> deleteById(Long id);
}
