package com.dbdesign.moviereview.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Movie", schema = "DBDESIGN")
public class Movie {
    @Id
    @Column(name = "movie_id", nullable = false)
    private Integer movie_id;

    @Size(max = 200)
    @NotNull
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Size(max = 100)
    @Column(name = "director", length = 100)
    private String director;

    @Column(name = "release_date")
    private LocalDate release_date;

    @Size(max = 50)
    @Column(name = "genre", length = 50)
    private String genre;

    @Lob
    @Column(name = "description")
    private String description;

}