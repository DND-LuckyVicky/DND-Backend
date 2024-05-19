package com.luckyvicky.dndbackend;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.luckyvicky.dndbackend.dto.*;
import com.luckyvicky.dndbackend.dto.FinalResults;
import com.luckyvicky.dndbackend.hn.Destinations;
import jakarta.servlet.http.Cookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class Controller {

	private final Map<String, String> idMap = new ConcurrentHashMap<>();
	private static final Logger log = LoggerFactory.getLogger(Controller.class);
	private final Service service;

	@PostMapping("/api/destination")
	public Response saveDestination(
		HttpServletRequest request,
		@RequestBody MapPosition mapPosition
	) {
		String userId = request.getRemoteAddr();
		var uuid = UUID.randomUUID().toString();
		idMap.put(userId, uuid);

		service.saveDestination(mapPosition, uuid);

		return new Response(true);
	}

	@GetMapping("/api/destination")
	public Destinations getDestination(
		HttpServletRequest request
	) {
		var userId = request.getRemoteAddr();
		var uuid = idMap.get(userId);

		return service.getDestination(uuid);
	}

	@PostMapping("/api/destination/select")
	public Response selectDestination(
		HttpServletRequest request,
		@RequestBody SelectedDestination selectedDestination
	) {
		var userId = request.getRemoteAddr();
		var uuid = idMap.get(userId);
		service.selectDestination(selectedDestination, uuid);

		return new Response(true);
	}

	@GetMapping("/api/result")
	public Results getResult(
		HttpServletRequest request
	) {
		var userId = request.getRemoteAddr();
		var uuid = idMap.get(userId);

		return service.getResult(uuid);
	}

	@PostMapping("/api/save-gotcha")
	public Response saveGotcha(
		HttpServletRequest request,
		@RequestBody Gotcha gotcha
	) {
		var userId = request.getRemoteAddr();
		var uuid = idMap.get(userId);
		service.saveGotcha(gotcha, uuid);

		return new Response(true);
	}

	@PostMapping("/api/final-result")
	public UserId saveFinalResult(
		HttpServletRequest request,
		@RequestBody FinalResultRequest finalResultRequest
	) {
		var userId = request.getRemoteAddr();
		var uuid = idMap.get(userId);
		service.saveFinalResult(finalResultRequest, uuid);

		return new UserId(uuid);
	}

	@GetMapping("/api/final-result/{userId}")
	public FinalResults getFinalResult(
		@PathVariable String userId
	) {
		return service.getFinalResult(userId);
	}

	@PostMapping("/api/random-gotcha")
	public String selectRandomGotcha(
		@RequestBody SelectedRandomGotcha selectedRandomGotcha
	) {
		service.selectRandomGotcha(selectedRandomGotcha);

		return selectedRandomGotcha.userId();
	}

	@GetMapping("/api/final-result/polling")
	public PollingResult getPollingResult(
		HttpServletRequest request
	) {
		var userId = request.getRemoteAddr();
		var uuid = idMap.get(userId);

		return service.getPollingResult(uuid);
	}
}
