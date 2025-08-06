package com.hari.tamil_movies.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "TAMIL_MOVIES")
@Data
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String movieName;
    private String genre;
    private Double rating;
    private String director;
    private String actor;
    private String peopleVote;
    private String year;
    private Double heroRating;
    private Double movieRating;
    private Double contentRating;

}
