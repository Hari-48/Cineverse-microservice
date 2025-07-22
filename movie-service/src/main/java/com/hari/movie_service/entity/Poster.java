package com.hari.movie_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "POSTER")
public class Poster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "URL")
    private String url;


    @Column(name = "VERSION_TAG")
    private String versionTag;

    @Column(name = "IS_ACTIVE")
    private String isActive;

    @ManyToOne
    @JoinColumn(name = "MOVIE_ID")
    @JsonIgnore
    private Movie movie;



}
