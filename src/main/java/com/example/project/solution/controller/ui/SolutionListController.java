package com.example.project.solution.controller.ui;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.auth.service.UserAuthService;
import com.example.project.common.util.HeaderUtil;
import com.example.project.solution.dto.response.list.NonAuthSolutionListResponse;
import com.example.project.solution.dto.response.list.AuthSolutionListResponse;
import com.example.project.solution.service.UserSolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/solutions/list")
@RequiredArgsConstructor
public class SolutionListController {

    private final UserSolutionService userSolutionService;
    private final UserAuthService userAuthService;
    private final AuthTokenService authTokenService;

    @RequestMapping
    public String solutionList(
            Model model,
            @CookieValue(value = HeaderUtil.AUTHORIZATION_HEADER, required = false) String cookie
    ) {
        log.info("solutionList entry -> token: {}", cookie);
        if (authTokenService.isValidateToken(cookie)) {
            Long userIdByToken = userAuthService.getUserIdByToken(cookie);
            log.info("token is validate -> user: {}", userIdByToken);

            List<AuthSolutionListResponse> authSolutionListRespons = userSolutionService.getAuthSolutionList(userIdByToken);
            log.info("solutionListResponse size: {}", authSolutionListRespons.size());

            model.addAttribute("SolutionList", authSolutionListRespons);
            return "authentication/solution/solution_list";
        } else {
            log.info("token is not validate");

            List<NonAuthSolutionListResponse> nonAuthSolutionList = userSolutionService.getNonAuthSolutionList();
            log.info("solutionListResponse size: {}", nonAuthSolutionList.size());

            model.addAttribute("SolutionList", nonAuthSolutionList);
            return "non-authentication/solution/solution_list";
        }
    }
}
