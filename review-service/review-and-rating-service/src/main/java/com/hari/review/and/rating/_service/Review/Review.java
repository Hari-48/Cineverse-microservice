package com.hari.review.and.rating._service.Review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "REVIEW")
@Data

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;


    @Column(name = "MOVIE_ID")
    private Long movieId;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "RATING")
    private Integer rating;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(name = "USER_ID")
    private Long userId;



    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }





}
