package com.luckyvicky.dndbackend.ys;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.luckyvicky.dndbackend.dto.PollingResult;
import com.luckyvicky.dndbackend.dto.SelectedRandomGotcha;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class YsService {
	private final Map<String, Object> data = new ConcurrentHashMap<>();

	public void saveFinalResult(Long statementId, String userId) {

	}

	public FinalResults getFinalResult(String userId) {
		return null;
	}

	public void selectRandomGotcha(SelectedRandomGotcha selectedRandomGotcha) {

	}

	public PollingResult getPollingResult(String userId) {
		return null;
	}
}
