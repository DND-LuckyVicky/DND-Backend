package com.luckyvicky.dndbackend.hn;

import java.util.List;

public record OdsaySearchResponse(
    Result result
) {

    record Result(
        List<Path> path
    ) {
    }
}
