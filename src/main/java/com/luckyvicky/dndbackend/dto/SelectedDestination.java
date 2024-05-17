package com.luckyvicky.dndbackend.dto;

public record SelectedDestination(
	String vehicle,
	int totalMinutes,
	int totalPayment,
	int transitCount,
	String firstStartStation,
	String lastEndStation,
	String totalDistance
) {
}
