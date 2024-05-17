package com.luckyvicky.dndbackend.dto;

public record PollingResult(
	String endName,
	int totalMinutes,
	int totalPayment,
	RandomGotcha randomGotchas,
	boolean isSuccess
) {
}
