package com.dbdesign.moviereview.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class GetMovieDTO {
    private List<Integer> genre_ids;
    private int id;
    private String overview;
    private String poster_path;
    private String release_date;
    private String title;
}
