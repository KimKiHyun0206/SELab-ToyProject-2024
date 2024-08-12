package com.example.project.controller;

import com.example.project.dto.LoginForm;
import com.example.project.dto.SignupForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping
    public String main(){
        return "main";
    }

    @RequestMapping("/signup")
    public String signup(Model model){
        model.addAttribute("signupForm", new SignupForm());
        return "non-authentication/user/signup";
    }

    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("loginForm", new LoginForm());
        return "non-authentication/user/login";
    }


    @RequestMapping("/solution-list")
    public String solutionList(){
        return "authentication/solution/solution_list";
    }

    @RequestMapping("/my-page/{id}")
    public String myPage(@PathVariable String id, Model model){
        model.addAttribute("id",id);
        return "authentication/user/my_page";
    }
}
