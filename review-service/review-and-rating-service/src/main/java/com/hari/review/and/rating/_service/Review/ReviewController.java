package com.hari.review.and.rating._service.Review;

import com.hari.review.and.rating._service.model.ReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/add")
    public ResponseEntity<?> postReviewI(@RequestBody ReviewRequest request) {
        return reviewService.postReview(request);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@RequestBody ReviewRequest request, @PathVariable Long id) {
        return reviewService.updateReview(request,id);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<?> getAllMovieReview(@PathVariable Long movieId){
        return reviewService.getAllMovieReview(movieId);
    }


    @GetMapping("/movies/{movieId}/rating")
    public ResponseEntity<?> getMovieRating(@PathVariable Long movieId){
        return reviewService.getMovieRating(movieId);
    }

}
