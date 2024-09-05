package com.example.project.solution.controller.ui;

import com.example.project.solution.dto.response.SolutionListResponse;
import com.example.project.solution.service.UserSolutionService;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/solutions")
@RequiredArgsConstructor
public class SolutionController {

    private final UserSolutionService userSolutionService;
    private final SessionService sessionService;

    @RequestMapping("/list")
    public String solutionList(
            @PageableDefault(size = 10) Pageable pageable,
            Model model,
            HttpServletRequest request,
            @CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue
    ) {
        List<SolutionListResponse> solutionList = userSolutionService.getSolutionList(pageable);
        model.addAttribute("solutionList", solutionList);

        if(cookieValue != null){
            UserResponse user = sessionService.getUser(request, cookieValue);
            model.addAttribute("User", user);
            return "authentication/solution/solution_list";
        }

        return "non-authentication/solution/solution_list";

    }

    @RequestMapping("/solve/{id}")
    public String solvePage(
            @PathVariable(name = "id") Long id,
            Model model,
            HttpServletRequest request,
            @CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue
    ) {
        var response = userSolutionService.read(id);
        model.addAttribute("title", response.getTitle());
        model.addAttribute("description", response.getDescription());
        model.addAttribute("inExample", response.getInExample());
        model.addAttribute("outExample", response.getOutExample());

        UserResponse userResponse = sessionService.getUser(request, cookieValue);
        if (userResponse != null) {
            return "authentication/solution/solve";
        }
        return "non-authentication/solution/solve";
    }
}
