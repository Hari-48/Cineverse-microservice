package com.hari.movie_service.service.Implementation;

import com.hari.movie_service.entity.Cast;
import com.hari.movie_service.entity.Movie;
import com.hari.movie_service.repository.CastRepo;
import com.hari.movie_service.repository.MovieRepo;
import com.hari.movie_service.service.CastService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CastImpl implements CastService {

    private final CastRepo castRepo;

    private final MovieRepo movieRepo;


    @Override
    @Transactional
    public ResponseEntity<Movie> addCastToMovie(Long movieId, List<Cast> castForMovie) {
        Optional<Movie> optionalMovie = movieRepo.findByIdWithCasts(movieId);  // use custom fetch method

        log.info("Optional Movie :{}",optionalMovie);

        if (!optionalMovie.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Movie movie = optionalMovie.get();

        // Use a completely new set to avoid modifying Hibernate proxy collection
        Set<Cast> updatedCastSet = new HashSet<>(movie.getCasts());

        for (Cast cm : castForMovie) {
            Optional<Cast> existingCast = castRepo.findByName(cm.getName());

            Cast castToAdd;
            if (existingCast.isPresent()) {
                castToAdd = existingCast.get();
            } else {
                Cast newCast = new Cast();
                newCast.setName(cm.getName());
                newCast.setRole(cm.getRole());
                castToAdd = castRepo.save(newCast);  // Save new cast first
            }

            updatedCastSet.add(castToAdd);
        }

        movie.setCasts(updatedCastSet); // Replace the whole set safely
        movieRepo.save(movie);

        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @Override
    public List<Map<String, Object>> getCast(Long movieId) {
        return castRepo.findCastByMovieId(movieId);
    }


}

