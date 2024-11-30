package com.dbdesign.moviereview.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoUserDTO {
    public Long id; // 고유 id
    public String nickname; // 카카오톡 상의 이름
}