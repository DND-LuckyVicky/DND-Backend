package com.luckyvicky.dndbackend.hn;

import com.luckyvicky.dndbackend.entity.repository.TempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HnService {
    private final TempRepository tempRepository;
}
