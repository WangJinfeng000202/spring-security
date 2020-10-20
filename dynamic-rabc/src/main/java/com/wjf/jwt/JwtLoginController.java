//package com.wjf.jwt;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//public class JwtLoginController {
//    @Autowired
//    JwtAuthService jwtAuthService;
//
//
//    @PostMapping({"/login","/"})
//    public Map<String,Object> login(String username,String password){
//        String token = jwtAuthService.login(username,password);
//        Map<String,Object> map = new HashMap<>();
//        map.put("token",token);
//        return map;
//    }
//}
