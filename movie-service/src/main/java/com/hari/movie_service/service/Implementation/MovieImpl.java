package com.hari.movie_service.service.Implementation;

import com.hari.movie_service.DTO.GenreResponse;
import com.hari.movie_service.DTO.MovieRequest;
import com.hari.movie_service.DTO.MovieResponse;
import com.hari.movie_service.DTO.ShowTimingRequest;
import com.hari.movie_service.entity.Genre;
import com.hari.movie_service.entity.Movie;
import com.hari.movie_service.entity.ShowTime;
import com.hari.movie_service.repository.GenreRepo;
import com.hari.movie_service.repository.MovieRepo;
import com.hari.movie_service.service.MovieService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieImpl implements MovieService {

    private  final MovieRepo movieRepo;
    private final GenreRepo genreRepo;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<MovieResponse>> getAllMovies() {

        List<Movie> entities = movieRepo.findAll();
        List<MovieResponse> responseList = new ArrayList<>();
        for (Movie movie : entities) {
            MovieResponse movieResponse = new MovieResponse();
            movieResponse.setId(movie.getId());
            movieResponse.setTitle(movie.getTitle());
            movieResponse.setLanguage(movie.getLanguage());
            movieResponse.setReleaseDate(movie.getReleaseDate());

            // Map genre to GenreResponse DTO
            Genre genre = movie.getGenre();
            if (genre != null) {
                GenreResponse genreResponse = new GenreResponse();
                genreResponse.setId(genre.getId());
                genreResponse.setName(genre.getName());
                movieResponse.setGenre(genreResponse);
            }






            responseList.add(movieResponse);
        }

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Movie> saveMovie(MovieRequest request) {
        // Fetch genre from DB
        Genre genre = genreRepo.findById(request.getGenreId())
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        Movie movie = new Movie();
        Optional<Movie> response = Optional.empty();
        if (!(request.getId() == null)) {
            response = movieRepo.findById(request.getId());
        }
        if (Objects.requireNonNull(response).isPresent()) {
            movie.setId(request.getId());
        }
        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setLanguage(request.getLanguage());
        movie.setReleaseDate(request.getReleaseDate());
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());
        movie.setGenre(genre);
        // Save movie
        Movie savedMovie = movieRepo.save(movie);
        return new ResponseEntity<>(savedMovie, HttpStatus.OK);

    }


    @Override
    public ResponseEntity<List<Map<String,Object>>> getAllMoviesWithGenre() {
        List<Map<String,Object>> movies =  movieRepo.findAllWithGenre();
        return new ResponseEntity<>(movies,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        List<Map<String,Object>> movies =  movieRepo.findMovies();
        return new ResponseEntity<>(movies,HttpStatus.OK);
    }
    @Override
    public ResponseEntity<List<Map<String,Object>>> getMovieById(Long id) {
        List<Map<String,Object>> movie = movieRepo.findByMovieId(id);
        return  new ResponseEntity<>(movie,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        movieRepo.deleteById(id);
        return new ResponseEntity<>("DELETED SUCCESSFULLY",HttpStatus.OK);
    }
}

