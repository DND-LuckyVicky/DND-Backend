package com.luckyvicky.dndbackend.dto;

import java.util.List;

public record FinalResults(
	String endName,
	int totalMinutes,
	int totalPayment,
	List<RandomGotcha> randomGotchas,
	RecommendStatement recommendStatement
) {

	static FinalResults from(FinalResults finalResults, RandomGotcha newRandomGotcha) {
		return new FinalResults(finalResults.endName(), finalResults.totalMinutes(), finalResults.totalPayment(),
			List.of(newRandomGotcha), finalResults.recommendStatement());
	}
}
