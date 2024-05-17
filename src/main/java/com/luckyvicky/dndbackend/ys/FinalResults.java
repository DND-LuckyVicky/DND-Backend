package com.luckyvicky.dndbackend.ys;

import java.util.List;

import com.luckyvicky.dndbackend.dto.RandomGotcha;
import com.luckyvicky.dndbackend.dto.RecommendStatement;

public record FinalResults(
	String endName,
	int totalMinutes,
	int totalPayment,
	List<RandomGotcha> randomGotchas,
	RecommendStatement recommendStatement
) {
}
