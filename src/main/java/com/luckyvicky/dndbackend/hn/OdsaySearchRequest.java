package com.luckyvicky.dndbackend.hn;

import com.luckyvicky.dndbackend.dto.MapPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OdsaySearchRequest {

    private String apiKey;
    private Double SX;
    private Double SY;
    private Double EX;
    private Double EY;

    public static OdsaySearchRequest of(
        String apiKey,
        MapPosition mapPosition
    ) {
        return OdsaySearchRequest.builder()
            .apiKey(apiKey)
            .SX(mapPosition.startLong())
            .SY(mapPosition.startLat())
            .EX(mapPosition.endLong())
            .EY(mapPosition.endLat())
            .build();
    }
}
