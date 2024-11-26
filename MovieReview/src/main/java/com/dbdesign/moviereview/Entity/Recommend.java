package com.dbdesign.moviereview.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "Recommend", schema = "DBDESIGN")
public class Recommend {
    @EmbeddedId
    private RecommendId id;

    @MapsId("user_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user_id;

    @MapsId("movie_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie_id;

    @Column(name = "recommend_date")
    private LocalDateTime recommend_date;

}