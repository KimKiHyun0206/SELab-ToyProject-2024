package com.example.project.auth.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class AuthHandlerController {

    @RequestMapping("/403")
    public String forbidden(){
        return "/non-auth/error/403";
    }

    @RequestMapping("/401")
    public String unauthorized(){
        return "/non-auth/error/401";
    }
}
