package com.dbdesign.moviereview.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class RecommendId implements java.io.Serializable {
    private static final long serialVersionUID = -382735809327251353L;
    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @NotNull
    @Column(name = "movie_id", nullable = false)
    private Integer movie_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RecommendId entity = (RecommendId) o;
        return Objects.equals(this.movie_id, entity.movie_id) &&
                Objects.equals(this.user_id, entity.user_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie_id, user_id);
    }

}