package com.luckyvicky.dndbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public record Results(
	Data data
) {
	public void setGotcha(List<RandomGotcha> randomGotchas) {
		data.setRandomGotchas(randomGotchas);
	}

	public static Results of(
		List<String> vehicles,
		String endName,
		int totalMinutes,
		int totalPayment,
		String jokeByPrice,
		String jokeByTime,
		List<RandomGotcha> randomGotchas,
		List<RecommendStatement> recommendStatements
	) {
		return new Results(
			new Data(
				vehicles,
				endName,
				totalMinutes,
				totalPayment,
				jokeByPrice,
				jokeByTime,
				randomGotchas,
				recommendStatements
			)
		);
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Data {
		List<String> vehicles;
		String endName;
		int totalMinutes;
		int totalPayment;
		String jokeByPrice;
		String jokeByTime;
		List<RandomGotcha> randomGotchas;
		List<RecommendStatement> recommendStatements;
	}
}
