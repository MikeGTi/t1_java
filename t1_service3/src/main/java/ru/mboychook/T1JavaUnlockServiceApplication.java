package ru.mboychook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@SpringBootApplication
@EnableConfigurationProperties
@Slf4j
public class T1JavaUnlockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(T1JavaUnlockServiceApplication.class, args);
    }

}
