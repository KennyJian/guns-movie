package com.kenny.movie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot方式启动类
 *
 * @author kenny
 * @Date 2018/10/30 12:06
 */
@SpringBootApplication
public class MovieApplication {

    private final static Logger logger = LoggerFactory.getLogger(MovieApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MovieApplication.class, args);
        logger.info("影院后台管理系统启动成功");
    }
}
