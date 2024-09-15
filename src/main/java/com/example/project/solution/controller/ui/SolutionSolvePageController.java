package com.example.project.solution.controller.ui;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.auth.service.UserAuthService;
import com.example.project.common.util.HeaderUtil;
import com.example.project.solution.service.UserSolutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/solutions")
@RequiredArgsConstructor
public class SolutionSolvePageController {
    private final UserSolutionService userSolutionService;
    private final AuthTokenService authTokenService;

    @RequestMapping("/{id}")
    public String solvePage(
            @PathVariable(name = "id") Long id,
            Model model,
            @CookieValue(value = HeaderUtil.AUTHORIZATION_HEADER, required = false) String token
    ) {
        var response = userSolutionService.read(id);
        model.addAttribute("title", response.getTitle());
        model.addAttribute("description", response.getDescription());
        model.addAttribute("inExample", response.getInExample());
        model.addAttribute("outExample", response.getOutExample());
        if (authTokenService.isValidateToken(token)) {
            return "authentication/solution/solve";
        }
        return "non-authentication/solution/solve";
    }
}
