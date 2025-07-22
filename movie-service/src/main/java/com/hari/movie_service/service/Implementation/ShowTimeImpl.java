package com.hari.movie_service.service.Implementation;

import com.hari.movie_service.entity.Movie;
import com.hari.movie_service.entity.Poster;
import com.hari.movie_service.entity.ShowTime;
import com.hari.movie_service.repository.MovieRepo;
import com.hari.movie_service.repository.ShowTimeRepo;
import com.hari.movie_service.service.ShowTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowTimeImpl implements ShowTimeService {


    private final ShowTimeRepo showTimeRepo;
    private final MovieRepo movieRepo;

    @Override
    public ResponseEntity<Movie> addShowTime(Long movieId, ShowTime showTime) {
        Optional<Movie> optionalMovie = movieRepo.findById(movieId);
        if (optionalMovie.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Movie movie = optionalMovie.get();
        ShowTime st = new ShowTime();
        st.setLanguage(showTime.getLanguage());
        st.setSession(showTime.getSession());
        st.setDate(showTime.getDate());
        st.setTheatreName(showTime.getTheatreName());
        st.setTime(showTime.getTime());
        st.setMovie(movie);
        showTimeRepo.save(st);
        return new ResponseEntity<>(movie,HttpStatus.OK);
    }

    @Override
    public List<Map<String, Object>> getShowTimingByMovieId(Long movieId) {
      return showTimeRepo.findByMovieId(movieId);




    }
}
