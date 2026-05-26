package com.huy.aiagentsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello v3";
    }

    @GetMapping("/hello/cpu-test")
    public String cpuTest() {

        long sum = 0;

        for (long i = 0; i < 2_000_000_000L; i++) {
            sum += i;
        }

        return "CPU test done: " + sum;
    }
}