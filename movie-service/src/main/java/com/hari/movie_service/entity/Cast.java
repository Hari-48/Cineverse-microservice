package com.hari.movie_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "CAST")
@Data
public class Cast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "MOVIE_ID")
    private Long movie_id;


    @ManyToMany(mappedBy = "casts")
    private Set<Movie> movies;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cast)) return false;
        Cast cast = (Cast) o;
        return Objects.equals(id, cast.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }





}
