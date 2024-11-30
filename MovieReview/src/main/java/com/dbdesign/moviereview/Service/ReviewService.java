package com.dbdesign.moviereview.Service;

import com.dbdesign.moviereview.DTO.ReviewDTO;
import com.dbdesign.moviereview.Entity.Review;
import com.dbdesign.moviereview.Repository.MovieRepository;
import com.dbdesign.moviereview.Repository.ReviewRepository;
import com.dbdesign.moviereview.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    public List<Review> getReviewById(int movie_id) {
        return reviewRepository.findByMovieId(movie_id);
    }

    public void saveReview(ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .movie_id(movieRepository.findById(reviewDTO.getMovie_id()).get())
                .user_id(userRepository.findById(reviewDTO.getUser_id()).get())
                .rating(reviewDTO.getRating())
                .review_text(reviewDTO.getReview_text())
                .build();

        if (reviewDTO.getReview_id() != 0) {
            review.setReview_id(reviewDTO.getReview_id());
        }
        reviewRepository.save(review);
    }

    public void deleteReview(int review_id) {
        reviewRepository.deleteById(review_id);
    }
}
