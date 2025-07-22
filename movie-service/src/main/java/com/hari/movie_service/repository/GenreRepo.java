package com.hari.movie_service.repository;

import com.hari.movie_service.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepo  extends JpaRepository<Genre,Long> {
}
