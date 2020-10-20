package com.wjf.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @PostMapping("/toMain")
    public String main(){
        return "redirect:main.html";
    }

    @PostMapping("/toError")
    public String error(){
        return "redirect:error.html";
    }
}
