package com.luckyvicky.dndbackend.hn;

import com.luckyvicky.dndbackend.dto.Destination;
import com.luckyvicky.dndbackend.dto.MapPosition;
import com.luckyvicky.dndbackend.dto.Results;
import com.luckyvicky.dndbackend.entity.repository.TempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HnService {

    private final OdsayProperties odSayProperties;
    private final OdsayOpenFeign odsayOpenFeign;

    public List<Destination> saveDestination(MapPosition mapPosition, String userId) {
        var odsaySearchRequest = OdsaySearchRequest.of(
            odSayProperties.getApiKey(),
            mapPosition
        );
        var uri = URI.create(odSayProperties.getUrl());
        var odsaySearchResponse = odsayOpenFeign.searchPublicTransportation(
            uri,
            odsaySearchRequest
        );
        return odsaySearchResponse.result().path().stream()
            .map(Destination::of)
            .toList();
    }

    public void selectDestination(String userId) {
    }

    public Results getResult(String userId) {
        return null;
    }
}
