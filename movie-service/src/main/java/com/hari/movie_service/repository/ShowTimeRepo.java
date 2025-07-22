package com.hari.movie_service.repository;

import com.hari.movie_service.entity.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShowTimeRepo extends JpaRepository<ShowTime,Long> {
    @Query(value = "SELECT * FROM SHOW_TIME S WHERE S.MOVIE_ID = :movieId",nativeQuery = true)
    List<Map<String, Object>> findByMovieId(Long movieId);
}
