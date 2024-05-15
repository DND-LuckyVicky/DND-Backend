package com.luckyvicky.dndbackend.ys;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class YsController {
    private final YsService ysService;
}
