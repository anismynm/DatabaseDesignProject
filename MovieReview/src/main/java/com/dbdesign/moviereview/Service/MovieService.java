package com.dbdesign.moviereview.Service;

import com.dbdesign.moviereview.Entity.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovieService {
    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public String getMovies() {
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("api_key", apiKey)
                .queryParam("language", "ko-KR")
                .queryParam("page", 1)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);

        return response;
    }

//    public List<Movie> getAllMovies() {
//
//    }
//
//    public Movie getMovieById(int movie_id) {
//
//    }

}
