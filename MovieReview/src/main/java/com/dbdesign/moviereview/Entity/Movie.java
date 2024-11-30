package com.dbdesign.moviereview.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(name = "release_date")
    private LocalDate release_date;

    @Size(max = 50)
    @Column(name = "genre", length = 50)
    private String genre;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Lob
    @Column(name = "img_url", columnDefinition = "TEXT")
    private String img_url;
}