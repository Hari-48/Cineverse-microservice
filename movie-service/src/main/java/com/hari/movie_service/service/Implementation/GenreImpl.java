package com.hari.movie_service.service.Implementation;

import com.hari.movie_service.DTO.GenreResponse;
import com.hari.movie_service.entity.Genre;
import com.hari.movie_service.repository.GenreRepo;
import com.hari.movie_service.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreImpl implements GenreService {

    private final GenreRepo genreRepo;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<GenreResponse>> getAllGenre() {
        List<Genre> genres = genreRepo.findAll();
        List<GenreResponse> responses = genres.stream()
                .map(genre -> modelMapper.map(genre, GenreResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<GenreResponse> saveGenre(String genreName) {
        Genre genre = new Genre();
        genre.setName(genreName);
        Genre savedGenre = genreRepo.save(genre);
        GenreResponse response = modelMapper.map(savedGenre,GenreResponse.class);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
