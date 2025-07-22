package com.hari.review.and.rating._service.Review;

import com.hari.review.and.rating._service.Review.Review;
import com.hari.review.and.rating._service.model.ReviewRequest;
import com.hari.review.and.rating._service.Review.ReviewRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepo repo;

    private final EntityManager entityManager;
    public ResponseEntity<?> postReview(ReviewRequest request) {
        Long count = repo.getCount(request.getUserId());
        if(count!=0) {
            List<Review> reviews = repo.findByUserId(request.getUserId());
            for(Review review :reviews) {
                if (Objects.equals(review.getMovieId(), request.getMovieId())) {
                    return new ResponseEntity<>("Already reviewed", HttpStatus.OK);
                }
            }
        }
        Review rev = new Review();
        rev.setRating(request.getRating());
        rev.setMovieId(request.getMovieId());
        rev.setUserId(request.getUserId());
        rev.setComment(request.getComment());

        repo.save(rev);


        //repo.save(rev);
        return new ResponseEntity<>(repo.save(rev), HttpStatus.OK);
    }

    public ResponseEntity<?> updateReview(ReviewRequest request, Long id) {
        Optional<Review> review  = repo.findById(id);
        if(review.isEmpty()){
            return new ResponseEntity<>("NOT FOUND",HttpStatus.OK);
        }
        review.get().setComment(request.getComment());
        review .get().setUpdatedAt(LocalDateTime.now());
        review.get().setMovieId(request.getMovieId());
        review.get().setRating(request.getRating());
        review.get().setUserId(request.getUserId());
        return  new ResponseEntity<>(repo.saveAndFlush(review.get()),HttpStatus.OK);
    }

    public ResponseEntity<?> getAllMovieReview(Long movieId) {

        //Native Sql

        String sql = "SELECT * FROM REVIEW WHERE movie_id = :movieId";
        Query query = entityManager.createNativeQuery(sql, Review.class);
        query.setParameter("movieId", movieId);

        List<Review> reviews = query.getResultList();
        return ResponseEntity.ok(reviews);



        //JPQL
//        String jpql = "SELECT r FROM Review r WHERE r.movieId = :movieId";
//        Query query = entityManager.createQuery(jpql, Review.class);
//        query.setParameter("movieId", movieId);
//        List<Review> reviews = query.getResultList();
//        return ResponseEntity.ok(reviews);


//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Review> cq = cb.createQuery(Review.class);
//        Root<Review> root = cq.from(Review.class);
//        cq.select(root).where(cb.equal(root.get("movieId"), movieId));
//
//        List<Review> reviews = entityManager.createQuery(cq).getResultList();
//        return ResponseEntity.ok(reviews);
    }
    public ResponseEntity<?> getMovieRating(Long movieId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Review> root = cq.from(Review.class);
        cq.select(root.get("rating")).where(cb.equal(root.get("movieId"), movieId));

        List<Long> reviewsIds = entityManager.createQuery(cq).getResultList();
        return ResponseEntity.ok(reviewsIds);

    }
}
