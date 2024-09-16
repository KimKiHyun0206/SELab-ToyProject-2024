package com.example.project.board.controller.ui;

import com.example.project.auth.service.AuthTokenService;
import com.example.project.board.dto.BoardResponse;
import com.example.project.board.service.BoardService;
import com.example.project.common.util.HeaderUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final AuthTokenService authTokenService;

    @RequestMapping("/{solutionId}")
    public String boardList(
            @PathVariable(name = "solutionId") Long id,
            Model model,
            HttpServletRequest httpServletRequest
    ) {
        List<BoardResponse> boardResponses = boardService.readAllBySolutionId(id);
        model.addAttribute("Boards", boardResponses);

        if (authTokenService.isValidateToken(HeaderUtil.resolveToken(httpServletRequest))) {
            return "auth/board";
        } else return "non-auth/board";
    }
}