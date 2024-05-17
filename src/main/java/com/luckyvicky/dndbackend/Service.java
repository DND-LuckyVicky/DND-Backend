package com.luckyvicky.dndbackend;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.luckyvicky.dndbackend.dto.Destination;
import com.luckyvicky.dndbackend.dto.MapPosition;
import com.luckyvicky.dndbackend.dto.RandomGotcha;
import com.luckyvicky.dndbackend.dto.RecommendStatement;
import com.luckyvicky.dndbackend.dto.Results;
import com.luckyvicky.dndbackend.dto.SelectedDestination;
import com.luckyvicky.dndbackend.hn.OdsayOpenFeign;
import com.luckyvicky.dndbackend.hn.OdsayProperties;
import com.luckyvicky.dndbackend.hn.OdsaySearchRequest;

@org.springframework.stereotype.Service
public class Service {
	private final Map<Integer, String> jokeByPriceMap = new ConcurrentHashMap<>();
	private final Map<Integer, String> jokeByTimeMap = new ConcurrentHashMap<>();

	private final Map<String, SelectedDestination> selectedDestinationMap = new ConcurrentHashMap<>();
	private final Map<Integer, RecommendStatement> recommendStatementMap = new ConcurrentHashMap<>();
	private final Map<Integer, List<RandomGotcha>> randomGotchaMap = new ConcurrentHashMap<>();
	private final OdsayProperties odSayProperties;
	private final OdsayOpenFeign odsayOpenFeign;

	public Service(OdsayProperties odSayProperties, OdsayOpenFeign odsayOpenFeign) {
		this.odSayProperties = odSayProperties;
		this.odsayOpenFeign = odsayOpenFeign;

		randomGotchaMap.put(5_000, List.of(new RandomGotcha(1,"5천가차1"), new RandomGotcha(2, "5천가차2")));
		randomGotchaMap.put(10_000, List.of("1만가차1", "1만가차2"));
		randomGotchaMap.put(30_000, List.of("3만가차1", "3만가차2"));
		randomGotchaMap.put(50_000, List.of("5만가차1", "5만가차2"));
		randomGotchaMap.put(100_000, List.of("10만가차1", "10만가차2"));
		randomGotchaMap.put(Integer.MAX_VALUE, List.of("이 정도면 너가 다~~ 사라 쫌!", "이 정도면 너가 다~~ 사라 쫌!"));

		recommendStatementMap.put(1, new RecommendStatement(1, "%s을 받을 수 있어서 좀 더 행복하게 서울에 도착할 수 있을 것 같아요!"));
		recommendStatementMap.put(2, new RecommendStatement(2, "서울까지 가는데, %s 사주면 안 잡아 먹짘ㅋㅋㅋ"));
		recommendStatementMap.put(3, new RecommendStatement(3, "너가 %s을 쏜다고? 알겠어~~"));
		recommendStatementMap.put(4, new RecommendStatement(4, "크으으 %s는 못 참지~~ 잘 먹으마"));
		recommendStatementMap.put(5, new RecommendStatement(5, "ㅠㅠ 내가 먼 길 가는데 %s 정도는 좀 사도..."));

		jokeByPriceMap.put(5_000, "스타벅스 아메리카노 한 잔");
		jokeByPriceMap.put(10_000, "만원짜리 맛있는게 하나.");
		jokeByPriceMap.put(30_000, "삼만원짜리 맛있는게 하나");
		jokeByPriceMap.put(50_000, "오만원짜리 맛있는게 하나");
		jokeByPriceMap.put(100_000, "십만원짜리 맛있는게 하나");

		jokeByTimeMap.put(60, "한시간 거리 넝담");
		jokeByTimeMap.put(120, "두 시간 거리 농다암");
		jokeByTimeMap.put(180, "세 시간 거리 농담쓰");
		jokeByTimeMap.put(240, "네시간 거리 농");
		jokeByTimeMap.put(300, "다섯시간 거리 농");
		jokeByTimeMap.put(360, "여섯 시간 농");
	}

	public List<Destination> saveDestination(MapPosition mapPosition, String userId) {
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
			.map(Destination::of)
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
		List<RecommendStatement> recommendStatements = getRecommendStatemnents(randomGotchas);

		return new Results(endName, totalMinutes, totalPayment, jokeByPrice, jokeByTime, randomGotchas,
			recommendStatements);
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
}
