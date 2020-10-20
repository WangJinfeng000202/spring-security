package com.wjf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


class DynamicRabcApplicationTests {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Test
    void contextLoads() {
        System.out.println(passwordEncoder.encode("123456"));
    }

}
