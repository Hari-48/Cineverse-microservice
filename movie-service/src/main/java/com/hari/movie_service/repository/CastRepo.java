package com.hari.movie_service.repository;

import com.hari.movie_service.entity.Cast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CastRepo extends JpaRepository<Cast,Long> {
    Optional<Cast> findByName(String name);


    @Query(value = "SELECT c.name, c.role\n" +
            "FROM movies_cast mc\n" +
            "JOIN `CAST`  c ON mc.cast_id = c.id\n" +
            "WHERE mc.movie_id = :movieId",nativeQuery = true)
    List<Map<String, Object>> findCastByMovieId(Long movieId);
}
