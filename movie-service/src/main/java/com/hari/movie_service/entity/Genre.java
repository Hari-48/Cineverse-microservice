package com.hari.movie_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "GENRE")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name ;

    @OneToMany(mappedBy = "genre")
    private List<Movie> movies;

}
