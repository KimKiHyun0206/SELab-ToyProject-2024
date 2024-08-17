package com.example.project.board.controller.ui;

import com.example.project.board.dto.BoardResponse;
import com.example.project.board.service.BoardService;
import com.example.project.user.dto.UserResponse;
import com.example.project.user.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final SessionService sessionService;


    //TODO Board 조회 가능한 페이지 만들기
    @RequestMapping("/{solutionId}")
    public String boardList(
            @PathVariable(name = "solutionId") Long id,
            @CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue,
            Model model,
            HttpServletRequest request
    ) {
        UserResponse userResponse = sessionService.getUser(request, cookieValue);


        List<BoardResponse> boardResponses = boardService.readAllBySolutionId(id);
        model.addAttribute("Boards", boardResponses);

        if(userResponse != null){
            return null;    //로그인된 상태의 HTML
        }

        return null;        //로그인 되지 않은 상태의 HTML
    }
}
