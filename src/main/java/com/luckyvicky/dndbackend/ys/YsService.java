package com.luckyvicky.dndbackend.ys;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.luckyvicky.dndbackend.dto.Destination;
import com.luckyvicky.dndbackend.dto.MapPosition;
import com.luckyvicky.dndbackend.dto.PollingResult;
import com.luckyvicky.dndbackend.dto.Results;
import com.luckyvicky.dndbackend.dto.SelectedRandomGotcha;
import com.luckyvicky.dndbackend.entity.repository.TempRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class YsService {
    private final TempRepository tempRepository;
	private final Map<String, Object> data = new ConcurrentHashMap<>();

	public List<Destination> saveDestination(MapPosition mapPosition, String userId) {

		// do something
		return null;
	}

	public void selectDestination(String userId) {

	}

	public Results getResult(String userId) {
		return null;
	}

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
