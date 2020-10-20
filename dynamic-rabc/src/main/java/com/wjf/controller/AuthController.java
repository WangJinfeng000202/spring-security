package com.wjf.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {

    @GetMapping("/hello")
    public String hello(){
        log.info("进入Hello");
        return "world";
    }

    @PostMapping("/product")
    public String product(){
        return "product";
    }
}
