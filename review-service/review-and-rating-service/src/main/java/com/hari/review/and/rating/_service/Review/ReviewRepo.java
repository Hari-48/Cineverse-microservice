package com.hari.review.and.rating._service.Review;

import com.hari.review.and.rating._service.Review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo  extends JpaRepository<Review,Long> {

    @Query(value = "select count(*) from REVIEW where USER_ID =:userId",nativeQuery = true)
    Long getCount(Long userId);

    @Query(value = "select * from REVIEW where USER_ID =:userId",nativeQuery = true)
    List<Review> findByUserId(Long userId);
}
