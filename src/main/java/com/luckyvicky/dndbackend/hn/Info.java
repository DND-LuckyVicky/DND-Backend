package com.luckyvicky.dndbackend.hn;

public record Info(
    int totalTime,
    int payment,
    int busTransitCount,
    int subwayTransitCount,
    String firstStartStation,
    String lastEndStation,
    long totalDistance,
    // 시외 교통
    int totalPayment,
    int transitCount
) {
}
