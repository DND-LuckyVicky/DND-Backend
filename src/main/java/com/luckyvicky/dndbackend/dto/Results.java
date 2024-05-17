package com.luckyvicky.dndbackend.dto;

import java.util.List;

public record Results(
	String endName,
	int totalMinutes,
	int totalPayment,
	String jokeByPrice,
	String jokeByTime,
	List<RandomGotcha> randomGotchas,
	List<RecommendStatement> recommendStatements
) {
}
