package com.wjf.jwt;

import com.wjf.exception.AjaxResponse;
import com.wjf.exception.CustomException;
import com.wjf.exception.CustomExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
public class JwtAuthController {

    @Autowired
    JwtAuthService jwtAuthService;

    @PostMapping("/authentication")
    public AjaxResponse login(@RequestBody Map<String,String> map){
        log.info("欢迎进入登录");
        String username  = map.get("username");
        String password = map.get("password");
        log.info("用户名[{}],密码[{}]",username,password);
        if(StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)){
            return AjaxResponse.error(
                    new CustomException(CustomExceptionType.USER_INPUT_ERROR,
                            "用户名或者密码不能为空"));
        }
        try {
            return AjaxResponse.success(jwtAuthService.login(username, password));
        }catch (CustomException e){
            return AjaxResponse.error(e);
        }
    }

    @RequestMapping(value = "/refreshtoken")
    public  AjaxResponse refresh(@RequestHeader("${jwt.header}") String token){
        return AjaxResponse.success(jwtAuthService.refreshToken(token));
    }

}
