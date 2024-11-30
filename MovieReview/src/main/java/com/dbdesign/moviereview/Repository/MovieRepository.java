package com.dbdesign.moviereview.Repository;

import com.dbdesign.moviereview.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
}
