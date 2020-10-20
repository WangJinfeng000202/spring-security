package com.wjf.controller.login;


import com.wjf.entity.User;
import com.wjf.mapper.UserMapper;
import com.wjf.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    UserMapper userMapper;
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,String> login(String username,String password){
        Map<String,String> map = new HashMap<>();
        Map<String,String> map1 = new HashMap<>();
        User user = userMapper.loadUserByUserName(username);
        if (user!=null && password.equals(user.getPassword())){
            map.put("msg","登陆成功");

            map1.put("username",username);
            map.put("token", JWTUtil.getToken(map1));
        }
        return map;
    }
}
