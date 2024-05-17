package com.luckyvicky.dndbackend.hn;

import com.luckyvicky.dndbackend.dto.Destination;
import com.luckyvicky.dndbackend.dto.MapPosition;
import com.luckyvicky.dndbackend.dto.Results;
import com.luckyvicky.dndbackend.entity.repository.TempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class HnService {
    private final TempRepository tempRepository;

    public List<Destination> saveDestination(MapPosition mapPosition, String userId) {
        // do something
        return null;
    }

    public void selectDestination(String userId) {
    }

    public Results getResult(String userId) {
        return null;
    }
}
