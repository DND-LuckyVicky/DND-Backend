package com.luckyvicky.dndbackend.dto;

import java.util.List;

public record SelectedDestination(
	List<String> vehicles,
	int totalMinutes,
	int totalPayment,
	int transitCount,
	String firstStartStation,
	String lastEndStation,
	String totalDistance
) {
}
