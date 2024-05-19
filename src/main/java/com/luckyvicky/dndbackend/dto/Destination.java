package com.luckyvicky.dndbackend.dto;

import com.luckyvicky.dndbackend.hn.Path;
import com.luckyvicky.dndbackend.hn.Transport;

import java.util.List;

public record Destination(
	List<String> vehicles,
	int totalMinutes,
	int totalPayment,
	int transitCount,
	String firstStartStation,
	String lastEndStation,
	long totalDistance
) {

	public static Destination of(
		Path path
	) {
		var transport = Transport.from(path.pathType());
		var totalPayment = switch (transport) {
			case BUS, SUBWAY, SUBWAY_BUS ->  path.info().payment();
			case TRAIN, EXPRESS_BUS, AIRPLANE, TRANSPORT_COMBINE -> path.info().totalPayment();
		};
		var transistCount = switch (transport) {
			case BUS, SUBWAY, SUBWAY_BUS -> path.info().busTransitCount() + path.info().subwayTransitCount();
			case TRAIN, EXPRESS_BUS, AIRPLANE, TRANSPORT_COMBINE -> path.info().transitCount();
		};
		return new Destination(
			transport.getDescription(),
			path.info().totalTime(),
			totalPayment,
			transistCount,
			path.info().firstStartStation(),
			path.info().lastEndStation(),
			path.info().totalDistance()
		);
	}
}
