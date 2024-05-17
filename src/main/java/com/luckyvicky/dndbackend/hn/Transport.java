package com.luckyvicky.dndbackend.hn;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum Transport {

    SUBWAY(1, "지하철"),
    BUS(2, "버스"),
    SUBWAY_BUS(3,"지하철+버스"),
    TRAIN(11,"열차"),
    EXPRESS_BUS(12, "고속, 시외버스"),
    AIRPLANE(13,"비행기"),
    TRANSPORT_COMBINE(20,"시외교통 복합");

    private final Integer pathType;
    private final String description;

    public static Transport from(Integer pathType) {
        return Arrays.stream(Transport.values())
            .filter(transport -> Objects.equals(transport.pathType, pathType))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
