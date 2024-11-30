package com.dbdesign.moviereview.Repository;

import com.dbdesign.moviereview.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query(value = "select * from Review where movie_id = :movieId", nativeQuery = true)
    List<Review> findByMovieId(@Param("movieId") int movieId);

    @Query(value = "select avg(rating) from Review where movie_id = :movieId", nativeQuery = true)
    Float getAvg(@Param("movieId") int movieId);
}
