package com.luckyvicky.dndbackend.dto;

import java.util.List;

public record Results(
	String endName,
	int totalMinutes,
	int totalPayment,
	List<RandomGotcha> randomGotchas,
	List<RecommendStatement> recommendStatements
) {
}
