package com.luckyvicky.dndbackend.dto;

public record MapPosition(
	Double startLat,
	Double startLong,
	String startName,

	Double endLat,
	Double endLong,
	String endName
) {
}
