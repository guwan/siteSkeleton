package com.guwan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan("com.guwan.**")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
