package com.luckyvicky.dndbackend;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.luckyvicky.dndbackend.dto.*;
import com.luckyvicky.dndbackend.hn.OdsayOpenFeign;
import com.luckyvicky.dndbackend.hn.OdsayProperties;
import com.luckyvicky.dndbackend.hn.OdsaySearchRequest;
import com.luckyvicky.dndbackend.dto.FinalResults;

@org.springframework.stereotype.Service
public class Service {
	private final Map<Integer, String> jokeByPriceMap = new ConcurrentHashMap<>();
	private final Map<Integer, String> jokeByTimeMap = new ConcurrentHashMap<>();

	private final Map<String, MapPosition> mapPositionMap = new ConcurrentHashMap<>();
	private final Map<String, Results> resultsMap = new ConcurrentHashMap<>();
	private final Map<String, FinalResults> finalResultsMap = new ConcurrentHashMap<>();
	private final Map<String, PollingResult> pollingResultMap = new ConcurrentHashMap<>();

	private final Map<String, SelectedDestination> selectedDestinationMap = new ConcurrentHashMap<>();
	private final List<RecommendStatement> recommendStatementList = new ArrayList<>();
	private final Map<Integer, List<RandomGotcha>> randomGotchaMap = new ConcurrentHashMap<>();
	private final OdsayProperties odSayProperties;
	private final OdsayOpenFeign odsayOpenFeign;

	public Service(OdsayProperties odSayProperties, OdsayOpenFeign odsayOpenFeign) {
		this.odSayProperties = odSayProperties;
		this.odsayOpenFeign = odsayOpenFeign;

		randomGotchaMap.put(5_000, List.of(new RandomGotcha(1,"5천가차1"), new RandomGotcha(2, "5천가차2")));
		randomGotchaMap.put(10_000, List.of(new RandomGotcha(3,"1만가차1"), new RandomGotcha(4, "1만가차2")));
		randomGotchaMap.put(30_000, List.of(new RandomGotcha(5,"5천가차1"), new RandomGotcha(6, "5천가차2")));
		randomGotchaMap.put(50_000, List.of(new RandomGotcha(7,"5천가차1"), new RandomGotcha(8, "5천가차2")));
		randomGotchaMap.put(100_000, List.of(new RandomGotcha(9,"5천가차1"), new RandomGotcha(10, "5천가차2")));
		randomGotchaMap.put(Integer.MAX_VALUE, List.of(new RandomGotcha(11,"5천가차1"), new RandomGotcha(12, "5천가차2")));

		recommendStatementList.add(new RecommendStatement(1, "'영화값 반띵'으로 좀 더 행복하게 서울 갈지도?"));
		recommendStatementList.add(new RecommendStatement(2, "서울까지 가는데, 점심 후 디저트는 사주면 안 잡아 먹짘ㅋㅋㅋ"));
		recommendStatementList.add(new RecommendStatement(3, "먼 길 올라가는데, 교통비 반 띵 어떰?"));
		recommendStatementList.add(new RecommendStatement(4, "서울의 점심 값은 내가, 저녁 값은 네가!"));

		jokeByPriceMap.put(5_000, "편의점 우산 한 개");
		jokeByPriceMap.put(10_000, "영화관 티켓 한 장");
		jokeByPriceMap.put(30_000, "핸드크림 한 개");
		jokeByPriceMap.put(50_000, "축의금 한 번");
		jokeByPriceMap.put(100_000, "블루투스 스피커");
		jokeByPriceMap.put(Integer.MAX_VALUE, "포르쉐 카 한 대");

		jokeByTimeMap.put(60, "축구 경기 전반전");
		jokeByTimeMap.put(120, "영화 1편");
		jokeByTimeMap.put(180, "서울 - 도쿄 비행시간");
		jokeByTimeMap.put(240, "18홀 골프 라운드 1회");
		jokeByTimeMap.put(300, "서울 - 태국 비행시간");
		jokeByTimeMap.put(360, "최저 수면시간");
	}

	public void saveDestination(MapPosition mapPosition, String userId) {
		mapPositionMap.put(userId, mapPosition);
	}

	public List<Destination> getDestination(String userId) {
		MapPosition mapPosition = mapPositionMap.get(userId);
		var odsaySearchRequest = OdsaySearchRequest.of(
			odSayProperties.getApiKey(),
			mapPosition
		);
		var uri = URI.create(odSayProperties.getUrl());
		var odsaySearchResponse = odsayOpenFeign.searchPublicTransportation(
			uri,
			odsaySearchRequest
		);

		return odsaySearchResponse.result().path().stream()
			.map(res -> Destination.of(res, mapPosition))
			.toList();
	}

	public void selectDestination(SelectedDestination selectedDestination, String userId) {
		selectedDestinationMap.put(userId, selectedDestination);
	}

	public Results getResult(String userId) {
		SelectedDestination selectedDestination = selectedDestinationMap.get(userId);

		String endName = selectedDestination.lastEndStation();
		int totalMinutes = selectedDestination.totalMinutes();
		int totalPayment = selectedDestination.totalPayment();

		String jokeByPrice = getJokeByPrice(totalPayment);
		String jokeByTime = getJokeByTime(totalMinutes);

		List<RandomGotcha> randomGotchas = getRandomGotcha(totalPayment);

		Results results = new Results(endName, totalMinutes, totalPayment, jokeByPrice, jokeByTime, randomGotchas,
			recommendStatementList);
		resultsMap.put(userId, results);

		return results;
	}

	private List<RandomGotcha> getRandomGotcha(int totalPayment) {
		return randomGotchaMap.keySet().stream()
			.filter(key -> totalPayment < key)
			.map(randomGotchaMap::get)
			.findFirst()
			.orElseThrow(RuntimeException::new);
	}

	private String getJokeByTime(int totalMinutes) {
		return jokeByTimeMap.keySet().stream()
			.filter(key -> totalMinutes < key)
			.map(jokeByTimeMap::get)
			.findFirst()
			.orElseThrow(RuntimeException::new);
	}

	private String getJokeByPrice(int totalPayment) {
		return jokeByPriceMap.keySet().stream()
			.filter(key -> totalPayment < key)
			.map(jokeByPriceMap::get)
			.findFirst()
			.orElseThrow(RuntimeException::new);
	}

	public void saveFinalResult(FinalResultRequest finalResultRequest, String userId) {
		MapPosition mapPosition = mapPositionMap.get(userId);
		String endName = mapPosition.endName();

		SelectedDestination selectedDestination = selectedDestinationMap.get(userId);
		int totalMinutes = selectedDestination.totalMinutes();
		int totalPayment = selectedDestination.totalPayment();

		var results = resultsMap.get(userId);
		List<RandomGotcha> randomGotchas = results.randomGotchas();
		if (finalResultRequest.customGotcha() != null) {
			randomGotchas.add(new RandomGotcha(Integer.MAX_VALUE, finalResultRequest.customGotcha()));
		}

		RecommendStatement recommendStatement = null;
		if (finalResultRequest.statementId() != null) {
			recommendStatement = recommendStatementList.stream()
				.filter(rs -> Objects.equals(rs.id(), finalResultRequest.statementId()))
				.findFirst()
				.orElseThrow(RuntimeException::new);
		} else {
			recommendStatement = new RecommendStatement(Integer.MAX_VALUE, finalResultRequest.customStatement());
		}

		FinalResults finalResults = new FinalResults(endName, totalMinutes, totalPayment, randomGotchas, recommendStatement);
		finalResultsMap.put(userId, finalResults);
	}

	public FinalResults getFinalResult(String userId) {
		return finalResultsMap.get(userId);
	}

	public void selectRandomGotcha(SelectedRandomGotcha selectedRandomGotcha) {
		String userId = selectedRandomGotcha.userId();
		FinalResults finalResults = finalResultsMap.get(userId);
		RandomGotcha randomGotcha = finalResults.randomGotchas()
			.stream()
			.filter(rg -> Objects.equals(rg.id(), selectedRandomGotcha.gotchaId()))
			.findFirst()
			.orElseThrow(RuntimeException::new);
		PollingResult pollingResult = PollingResult.of(finalResults, randomGotcha);
		pollingResultMap.put(userId, pollingResult);
	}

	public PollingResult getPollingResult(String userId) {
		if (!pollingResultMap.containsKey(userId)) {
			return PollingResult.createFalseResult();
		}

		return pollingResultMap.get(userId);
	}
}
