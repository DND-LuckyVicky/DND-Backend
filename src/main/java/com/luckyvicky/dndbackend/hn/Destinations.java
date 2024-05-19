package com.luckyvicky.dndbackend.hn;

import com.luckyvicky.dndbackend.dto.Destination;
import com.luckyvicky.dndbackend.dto.MapPosition;

import java.util.List;

public record Destinations(
    String startName,
    String endName,
    List<Destination> destinations
) {

    public static Destinations of(
        String startName,
        String endName,
        List<Path> paths
    ) {
        var destinations = paths.stream()
            .map(Destination::of)
            .toList();
        return new Destinations(startName, endName, destinations);
    }
}
