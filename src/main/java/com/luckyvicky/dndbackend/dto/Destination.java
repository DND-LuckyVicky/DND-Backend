package com.luckyvicky.dndbackend.dto;

public record Destination(
	String startName,
	String endName,
	String vehicle,
	int totalMinutes,
	int totalPayment,
	int transitCount,
	String firstStartStation,
	String lastEndStation,
	String totalDistance
) {
}
