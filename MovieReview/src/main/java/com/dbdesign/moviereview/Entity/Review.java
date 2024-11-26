package com.dbdesign.moviereview.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Review", schema = "DBDESIGN")
public class Review {
    @Id
    @Column(name = "review_id", nullable = false)
    private Integer review_id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie_id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user_id;

    @NotNull
    @Column(name = "rating", nullable = false)
    private Float rating;

    @Lob
    @Column(name = "review_text")
    private String review_text;

    @Column(name = "review_date")
    private LocalDateTime review_date;

}