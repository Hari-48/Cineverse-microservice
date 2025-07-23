package com.hari.movie_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "MOVIE")
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "RELEASE_DATE")
    private String releaseDate;

    @Column(name = "LANGUAGE")
    private String language;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;


    //---------------------------------------------------------

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "GENRE_ID")
    @JsonIgnoreProperties({"movies"})
    private Genre genre;

    //----------------------------------------------------------------------

    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnoreProperties
    private List<ShowTime> showTimes;

//-------------------------------------------------------------------------------


    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JsonIgnoreProperties
    private List<Poster> posters;


    //--------------------------------------------------------------------


    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "movies_cast", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "cast_id"))
    @JsonIgnoreProperties({"movies"})
    private Set<Cast> casts = new HashSet<>();



}
