package com.dbdesign.moviereview.DTO;

import com.dbdesign.moviereview.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private int review_id;
    private int movie_id;
    private Long user_id;
    private Float rating;
    private String review_text;
}
