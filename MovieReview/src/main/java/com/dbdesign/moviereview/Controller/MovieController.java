package com.dbdesign.moviereview.Controller;

// import com.dbdesign.moviereview.Entity.Movie;
import com.dbdesign.moviereview.Service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // 메인 화면 (영화 리스트)
//    @GetMapping
//    public String listMovies(Model model) {
//
//        model.addAttribute("movies", movieService.getAllMovies());
//        return "index";
//    }
//
//    // 영화 상세 정보
//    @GetMapping("/{movie_id}")
//    public String movieDetail(@PathVariable int movie_id, Model model) {
//        model.addAttribute("movie", movieService.getMovieById(movie_id));
//        return "movie-detail";
//    }

    // 리뷰 작성 화면
    @GetMapping("/{id}/review")
    public String writeReview(@PathVariable Long id, Model model) {
        model.addAttribute("movieId", id);
        return "write-review";
    }

    @GetMapping("/get")
    public String getMovie() {
        String response = movieService.getMovies();
        log.info(response);
        return "ok";
    }
}
