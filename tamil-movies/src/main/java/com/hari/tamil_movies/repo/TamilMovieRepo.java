package com.hari.tamil_movies.repo;

import com.hari.tamil_movies.entity.Movies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TamilMovieRepo extends JpaRepository<Movies,Long> {


    @Query(value = """
            SELECT COUNT(ID) FROM TAMIL_MOVIES
            """,nativeQuery = true)
    Long getCount();



    @Query(value = "SELECT * FROM TAMIL_MOVIES", nativeQuery = true)
    Page<Movies> getAllData(Pageable pageable);

}
