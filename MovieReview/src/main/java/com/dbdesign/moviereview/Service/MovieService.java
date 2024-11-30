package com.dbdesign.moviereview.Service;

import com.dbdesign.moviereview.DTO.GetMovieDTO;
import com.dbdesign.moviereview.DTO.MovieListDTO;
import com.dbdesign.moviereview.Entity.Movie;
import com.dbdesign.moviereview.Repository.MovieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService {
    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.base.url}")
    private String baseUrl;

    private final MovieRepository movieRepository;
    private final RestTemplate restTemplate;

    public void getMovies() throws JsonProcessingException {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("api_key", apiKey)
                .queryParam("language", "ko-KR")
                .queryParam("page", 1)
                .queryParam("fields", "genre_ids,id,overview,poster_path,release_date,title")
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        MovieListDTO movieList = mapper.readValue(response, MovieListDTO.class);

        for (GetMovieDTO getMovieDTO : movieList.getResults()) {
            LocalDate localDate = LocalDate.parse(getMovieDTO.getRelease_date(), formatter);
            String genre = mapper.writeValueAsString(getMovieDTO.getGenre_ids());
            Movie movie = Movie.builder()
                    .movie_id(getMovieDTO.getId())
                    .title(getMovieDTO.getTitle())
                    .release_date(localDate)
                    .genre(genre)
                    .description(getMovieDTO.getOverview())
                    .img_url(getMovieDTO.getPoster_path())
                    .build();

            movieRepository.save(movie);
        }
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(int movie_id) {
        return movieRepository.findById(movie_id).orElse(null);
    }

}
