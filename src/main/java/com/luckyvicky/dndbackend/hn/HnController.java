package com.luckyvicky.dndbackend.hn;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HnController {
    private final HnService service;

    @GetMapping("/hello")
    public String hello() {
        return "hello world!";
    }
}
