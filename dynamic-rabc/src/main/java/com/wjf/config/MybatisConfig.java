package com.wjf.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.wjf.mapper")
public class MybatisConfig {
}
