package com.luckyvicky.dndbackend.hn;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum Transport {

    SUBWAY(1, List.of("지하철")),
    BUS(2, List.of("시내버스")),
    SUBWAY_BUS(3,List.of("지하철", "시내버스")),
    TRAIN(11,List.of("기차")),
    EXPRESS_BUS(12, List.of("시외버스")),
    AIRPLANE(13,List.of("비행기")),
    TRANSPORT_COMBINE(20,List.of("기차", "시외버스"));

    private final Integer pathType;
    private final List<String> description;

    public static Transport from(Integer pathType) {
        return Arrays.stream(Transport.values())
            .filter(transport -> Objects.equals(transport.pathType, pathType))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
