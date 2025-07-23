package com.hari.movie_service.repository;

import com.hari.movie_service.entity.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
public interface PosterRepo extends JpaRepository<Poster,Long> {

    @Query(value = "\n" +
            "select * from POSTER p  where p.MOVIE_ID = :id and IS_ACTIVE = 'Y'\n" +
            "\n",nativeQuery = true)
    List<Map<String, Object>> getActivePoster(Long id);


    @Modifying
    @Transactional

    @Query(value = "UPDATE POSTER\n" +
            "SET IS_ACTIVE = 'Y'\n" +
            "WHERE ID = :posterId ;\n",nativeQuery = true)
    int updatePosterActive(Long posterId);
}
