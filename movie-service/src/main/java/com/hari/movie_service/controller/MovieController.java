package com.hari.movie_service.controller;

import com.hari.movie_service.DTO.MovieRequest;
import com.hari.movie_service.DTO.MovieResponse;
import com.hari.movie_service.entity.Cast;
import com.hari.movie_service.entity.Movie;
import com.hari.movie_service.entity.Poster;
import com.hari.movie_service.entity.ShowTime;
import com.hari.movie_service.service.CastService;
import com.hari.movie_service.service.MovieService;
import com.hari.movie_service.service.PosterService;
import com.hari.movie_service.service.ShowTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;
    private final CastService castService;
    private  final PosterService posterService;
    private final ShowTimeService showTimeService;


    @GetMapping("/")
    public ResponseEntity<List<Map<String,Object>>> getAll(){
        return movieService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity <List<Map<String,Object>>> getMovieById(@PathVariable Long id){
        return movieService.getMovieById(id);
    }


    @PostMapping("/")
    public ResponseEntity<Movie> saveMovie(@RequestBody MovieRequest movie){
        return movieService.saveMovie(movie);
    }

    @PutMapping("/")
    public ResponseEntity<Movie> UpdateMovie(@RequestBody MovieRequest movie){
        return movieService.saveMovie(movie);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteMovieById(@RequestParam Long id ){
        return movieService.deleteById(id);
    }

//-------------------------------------------------------------------------------------------------------
    @PostMapping("/{id}/cast")
    public   ResponseEntity<Movie> addCast(@PathVariable Long id ,@RequestBody List<Cast> cast ){
        return  castService.addCastToMovie(id , cast);
    }

    @GetMapping("/{id}/cast")
    public List<Map<String,Object>> getCast(@PathVariable Long id){
        return  castService.getCast(id);
    }


  //  --------------------------------------------------------------------------------------
    @PostMapping("/{movieId}/poster")
    public ResponseEntity<Movie> addPoster(@PathVariable Long movieId , @RequestBody Poster poster){
        return posterService.savePoster(movieId,poster);
    }

    @GetMapping("/{id}/poster")
    public List<Map<String,Object>> getPoster(@PathVariable Long id){
        return  posterService.getPoster(id);
    }


    @PutMapping("/poster")
    public ResponseEntity<Poster> updatePosterActive(@RequestParam Long posterId){
        return posterService.updatePosterActive(posterId);
    }

    //---------------------------------------------------------------------------------------------------------

    @PostMapping("/{movieId}/show-timing")
    public ResponseEntity<Movie> addShowTime(@PathVariable Long movieId , @RequestBody ShowTime showTime){
        return showTimeService.addShowTime(movieId,showTime);
    }


    @GetMapping("/{movieId}/show")
    public List<Map<String,Object>> getShowsByMoviesId(@PathVariable Long movieId){
        return showTimeService.getShowTimingByMovieId(movieId);
    }

}
