package com.luckyvicky.dndbackend.dto;

public record FinalResultRequest(
	String customGotcha,
	Long statementId,
	String customStatement
) {
}
