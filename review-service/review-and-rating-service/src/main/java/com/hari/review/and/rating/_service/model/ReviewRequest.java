package com.hari.review.and.rating._service.model;

import lombok.Data;

@Data
public class ReviewRequest {
    private Long movieId;
    private Integer rating;
    private String comment;
    private Long userId;
}
