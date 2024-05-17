package com.luckyvicky.dndbackend.ys;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.luckyvicky.dndbackend.dto.FinalResultRequest;
import com.luckyvicky.dndbackend.dto.MapPosition;
import com.luckyvicky.dndbackend.dto.PollingResult;
import com.luckyvicky.dndbackend.dto.RandomGotcha;
import com.luckyvicky.dndbackend.dto.RecommendStatement;
import com.luckyvicky.dndbackend.dto.Results;
import com.luckyvicky.dndbackend.dto.SelectedDestination;
import com.luckyvicky.dndbackend.dto.SelectedRandomGotcha;

@Service
public class YsService {
	private static final AtomicInteger gotchaIdGenerator = new AtomicInteger(0);
	private final Map<Integer, List<String>> randomGotchaMap = new ConcurrentHashMap<>();
	private final Map<String, MapPosition> mapPositionMap = new ConcurrentHashMap<>();
	private final Map<String, SelectedDestination> selectedDestinationMap = new ConcurrentHashMap<>();
	private final Map<String, Results> resultsMap = new ConcurrentHashMap<>();
	private final Map<String, List<RandomGotcha>> userRandomGotchaMap = new ConcurrentHashMap<>();
	private final Map<Integer, RecommendStatement> recommendStatementMap = new ConcurrentHashMap<>();
	private final Map<String, FinalResults> finalResultsMap = new ConcurrentHashMap<>();
	private final Map<String, PollingResult> pollingResultMap = new ConcurrentHashMap<>();

	public YsService() {
		randomGotchaMap.put(5_000, List.of("5천가차1", "5천가차2", "5천가차3", "5천가차4", "5천가차5"));
		randomGotchaMap.put(10_000, List.of("1만가차1", "1만가차2", "1만가차3", "1만가차4", "1만가차5"));
		randomGotchaMap.put(30_000, List.of("3만가차1", "3만가차2", "3만가차3", "3만가차4", "3만가차5"));
		randomGotchaMap.put(50_000, List.of("5만가차1", "5만가차2", "5만가차3", "5만가차4", "5만가차5"));
		randomGotchaMap.put(100_000, List.of("10만가차1", "10만가차2", "10만가차3", "10만가차4", "10만가차5"));
		randomGotchaMap.put(Integer.MAX_VALUE, List.of("이 정도면 너가 다~~ 사라 쫌!"));

		recommendStatementMap.put(1, new RecommendStatement(1, "%s을 받을 수 있어서 좀 더 행복하게 서울에 도착할 수 있을 것 같아요!"));
		recommendStatementMap.put(2, new RecommendStatement(2, "서울까지 가는데, %s 사주면 안 잡아 먹짘ㅋㅋㅋ"));
		recommendStatementMap.put(3, new RecommendStatement(3, "너가 %s을 쏜다고? 알겠어~~"));
		recommendStatementMap.put(4, new RecommendStatement(4, "크으으 %s는 못 참지~~ 잘 먹으마"));
		recommendStatementMap.put(5, new RecommendStatement(5, "ㅠㅠ 내가 먼 길 가는데 %s 정도는 좀 사도..."));

	}

	public void selectDestination(SelectedDestination selectedDestination, String userId) {
		selectedDestinationMap.put(userId, selectedDestination);
	}

	public Results getResult(String userId) {
		SelectedDestination selectedDestination = selectedDestinationMap.get(userId);
		int totalPayment = selectedDestination.totalPayment();
		if (totalPayment <= 5_000) {
			List<String> gotChas = randomGotchaMap.get(5_000);

		} else if (totalPayment <= 10_000) {
			List<String> gotChas = randomGotchaMap.get(10_000);

		} else if (totalPayment <= 30_000) {
			List<String> gotChas = randomGotchaMap.get(30_000);

		} else if (totalPayment <= 50_000) {
			List<String> gotChas = randomGotchaMap.get(50_000);

		} else if (totalPayment <= 100_000) {
			List<String> gotChas = randomGotchaMap.get(100_000);

		} else {
			List<String> gotChas = randomGotchaMap.get(Integer.MAX_VALUE);

		}

		return null;
	}

	public void saveFinalResult(FinalResultRequest finalResultRequest, String userId) {
		MapPosition mapPosition = mapPositionMap.get(userId);
		String endName = mapPosition.endName();

		SelectedDestination selectedDestination = selectedDestinationMap.get(userId);
		int totalMinutes = selectedDestination.totalMinutes();
		int totalPayment = selectedDestination.totalPayment();

		List<RandomGotcha> randomGotchas = userRandomGotchaMap.get(userId);
		int gotchaId = gotchaIdGenerator.getAndIncrement();
		RandomGotcha customRandomGotcha = new RandomGotcha(gotchaId, finalResultRequest.customGotcha());
		randomGotchas.add(customRandomGotcha);

		long statementId = finalResultRequest.statementId();
		RecommendStatement recommendStatement = recommendStatementMap.get(statementId);

		FinalResults finalResults = new FinalResults(endName, totalMinutes, totalPayment, randomGotchas,
			recommendStatement);
		finalResultsMap.put(userId, finalResults);
	}

	public FinalResults getFinalResult(String userId) {
		return finalResultsMap.get(userId);
	}

	public void selectRandomGotcha(SelectedRandomGotcha selectedRandomGotcha) {
		if (!selectedRandomGotcha.isSelected()) {
			return;
		}

		String userId = selectedRandomGotcha.userId();
		FinalResults finalResults = finalResultsMap.get(userId);
		RandomGotcha filteredRandomGotcha = userRandomGotchaMap.get(userId)
			.stream()
			.filter(randomGotcha -> randomGotcha.id() == selectedRandomGotcha.gotchaId())
			.findAny()
			.orElse(new RandomGotcha(-999, "에러"));
		PollingResult pollingResult = PollingResult.of(finalResults, filteredRandomGotcha);
		pollingResultMap.put(userId, pollingResult);
	}

	public PollingResult getPollingResult(String userId) {
		if (!pollingResultMap.containsKey(userId)) {
			return PollingResult.createFalseResult();
		}

		return pollingResultMap.get(userId);
	}
}
