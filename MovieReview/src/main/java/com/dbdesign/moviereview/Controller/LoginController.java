package com.dbdesign.moviereview.Controller;

import com.dbdesign.moviereview.DTO.KakaoUserDTO;
import com.dbdesign.moviereview.Entity.User;
import com.dbdesign.moviereview.Service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/callback")
    public void callback(@RequestParam("code") String code, HttpSession session, HttpServletResponse response) throws IOException {
        String accessToken = loginService.getAccessTokenFromKakao(code);
        log.info("Controller --- accessToken: {}", accessToken);

        KakaoUserDTO kakaoInfo = loginService.getKakaoInfo(accessToken);
        User loginUser = User.builder()
                .user_id(kakaoInfo.getId())
                .name(kakaoInfo.getNickname())
                .build();
        boolean newUser = loginService.registerLogic(loginUser);
        session.setAttribute("loginUser", loginUser);

        if (newUser) {
            session.setAttribute("redirectUrl", "welcome");
        }
        else {
            session.setAttribute("redirectUrl", "login");
        }

        response.sendRedirect("/movies");
    }

    @PostMapping("/movies/clear-redirect-url")
    @ResponseBody
    public void clearRedirectUrl(HttpSession session) {
        session.removeAttribute("redirectUrl");
    }
}
