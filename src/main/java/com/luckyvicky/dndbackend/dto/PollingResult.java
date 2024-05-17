package com.luckyvicky.dndbackend.dto;

public record PollingResult(
	String endName,
	Integer totalMinutes,
	Integer totalPayment,
	RandomGotcha randomGotchas,
	boolean isSuccess
) {
	public static PollingResult createFalseResult() {
		return new PollingResult(null, null, null, null, false);
	}

	public static PollingResult of(FinalResults finalResults, RandomGotcha filteredRandomGotcha) {
		return new PollingResult(finalResults.endName(), finalResults.totalMinutes(), finalResults.totalMinutes(),
			filteredRandomGotcha, true);
	}
}
