package com.dbdesign.moviereview.Controller;

import com.dbdesign.moviereview.DTO.ReviewDTO;
import com.dbdesign.moviereview.Entity.Movie;
import com.dbdesign.moviereview.Entity.Review;
import com.dbdesign.moviereview.Entity.User;
import com.dbdesign.moviereview.Repository.MovieRepository;
import com.dbdesign.moviereview.Repository.ReviewRepository;
import com.dbdesign.moviereview.Service.MovieService;
import com.dbdesign.moviereview.Service.ReviewService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;

    @Value("${tmdb.image.url}")
    private String imageUrl;

    @Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MovieRepository movieRepository;

    // 메인 화면 (영화 리스트)
    @GetMapping
    public String listMovies(Model model, HttpSession session) {
//        get DB from TMDB API
//        try {
//            movieService.getMovies();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri;
        model.addAttribute("location", location);
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("tmdbImgUrl", imageUrl);

        String redirectUrl = (String) session.getAttribute("redirectUrl");
        model.addAttribute("redirectUrl", redirectUrl);

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null) {
            log.info("Main --- loginUser: " + loginUser.getName());
        }
        else {
            log.info("Main --- loginUser is null");
        }
        return "index";
    }

    // 영화 상세 정보
    @GetMapping("/{movie_id}")
    public String movieDetail(@PathVariable int movie_id, Model model, HttpSession session) {
        List<Review> reviewList = reviewRepository.findByMovieId(movie_id);
        Movie movie = movieRepository.findById(movie_id).get();
        Float rating_avg = reviewRepository.getAvg(movie_id);
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("loginUser", loginUser);
        }
        else {
            model.addAttribute("loginUser", new User());
        }
        if (rating_avg == null) {
            rating_avg = 0f;
        }

        model.addAttribute("movie", movie);
        model.addAttribute("reviews", reviewList);
        model.addAttribute("tmdbImgUrl", imageUrl);
        model.addAttribute("rating_avg", (float) Math.floor(rating_avg * 10) / 10);

        return "movie-detail";
    }

    // 리뷰 작성 화면
    @GetMapping("/{id}/review")
    public String writeReview(@PathVariable Long id, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("movieId", id);
        if (loginUser == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "로그인이 필요합니다.");
            return "redirect:/movies/" + id;
        }
        else {
            model.addAttribute("loginUser", loginUser.getUser_id());
        }

        return "write-review";
    }


    @PostMapping("/reviews")
    public String submitReview(@ModelAttribute ReviewDTO reviewDTO, RedirectAttributes redirectAttributes) {
        reviewService.saveReview(reviewDTO);
        if (reviewDTO.getReview_id() != 0) {
            redirectAttributes.addFlashAttribute("editMessage", "리뷰가 수정되었습니다.");
        }
        else {
            redirectAttributes.addFlashAttribute("submitMessage", "리뷰가 생성되었습니다.");
        }

        return "redirect:/movies/" + reviewDTO.getMovie_id();
    }

    @GetMapping("/{movie_id}/reviews/{review_id}/delete")
    public String deleteReview(@PathVariable int movie_id, @PathVariable int review_id, RedirectAttributes redirectAttributes) {
        reviewService.deleteReview(review_id);
        redirectAttributes.addFlashAttribute("deleteMessage", "리뷰가 삭제되었습니다.");
        return "redirect:/movies/" + movie_id;
    }

    @GetMapping("/{movie_id}/reviews/{review_id}/edit")
    public String editReview(@PathVariable int movie_id, @PathVariable int review_id, Model model, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser.getUser_id());
        model.addAttribute("movieId", movie_id);
        model.addAttribute("reviewId", review_id);
        return "edit-review";
    }
}
