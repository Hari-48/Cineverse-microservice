package com.hari.movie_service.controller;

import com.hari.movie_service.DTO.GenreResponse;
import com.hari.movie_service.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movie/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/")
    public ResponseEntity<List<GenreResponse>> getAllGenre(){
        return genreService.getAllGenre();
    }

    @PostMapping("/")
    public ResponseEntity<GenreResponse> saveAllGenre(@RequestParam String genreName){
        return genreService.saveGenre(genreName);
    }


}
