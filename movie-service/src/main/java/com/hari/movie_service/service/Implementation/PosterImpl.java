package com.hari.movie_service.service.Implementation;

import com.hari.movie_service.entity.Movie;
import com.hari.movie_service.entity.Poster;
import com.hari.movie_service.repository.MovieRepo;
import com.hari.movie_service.repository.PosterRepo;
import com.hari.movie_service.service.PosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PosterImpl implements PosterService {

    private final PosterRepo posterRepo ;

    private final MovieRepo movieRepo ;


    @Override
    public ResponseEntity<Movie> savePoster(Long movieId, Poster poster) {
        Optional<Movie> optionalMovie = movieRepo.findById(movieId);
        if (optionalMovie.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Movie movie = optionalMovie.get();

        Poster newPoster = new Poster();
        newPoster.setUrl(poster.getUrl());
        newPoster.setVersionTag(poster.getVersionTag());
        newPoster.setIsActive(poster.getIsActive());
        newPoster.setMovie(movie);  // set the movie relationship

       posterRepo.save(newPoster);

        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @Override
    public List<Map<String, Object>> getPoster(Long id) {
        return posterRepo.getActivePoster(id);
    }

    @Override
    public ResponseEntity<Poster> updatePosterActive(Long posterId) {
        int updated = posterRepo.updatePosterActive(posterId);

        if (updated == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // No record updated
        }

        Optional<Poster> poster = posterRepo.findById(posterId);
        return poster.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
