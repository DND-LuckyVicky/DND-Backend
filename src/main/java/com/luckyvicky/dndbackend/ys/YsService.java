package com.luckyvicky.dndbackend.ys;

import com.luckyvicky.dndbackend.entity.repository.TempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class YsService {
    private final TempRepository tempRepository;

}
