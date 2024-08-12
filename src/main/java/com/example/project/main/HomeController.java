package com.example.project.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping
    public String main(@CookieValue(value = "DigitalLoginCookie", required = false) String cookieValue){
        if(cookieValue == null){
            return "non-authentication/main";
        }
        return "authentication/main";
    }
}
