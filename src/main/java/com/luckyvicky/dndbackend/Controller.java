package com.luckyvicky.dndbackend;

import java.util.List;

import com.luckyvicky.dndbackend.dto.*;
import com.luckyvicky.dndbackend.dto.FinalResults;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class Controller {

	private final Service service;

	@PostMapping("/api/destination")
	public Response saveDestination(
		HttpServletRequest request,
		@RequestBody MapPosition mapPosition
	) {
		HttpSession session = request.getSession();
		String userId = request.getRemoteAddr();
		session.setAttribute("userId", userId);

		service.saveDestination(mapPosition, userId);

		return new Response(true);
	}

	@GetMapping("/api/destination")
	public List<Destination> getDestination(
		HttpServletRequest request
	) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");

		return service.getDestination(userId);
	}

	@PostMapping("/api/destination/select")
	public String selectDestination(
		HttpServletRequest request,
		@RequestBody SelectedDestination selectedDestination
	) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		service.selectDestination(selectedDestination, userId);

		return userId;
	}

	@GetMapping("/api/result")
	public Results getResult(
		HttpServletRequest request
	) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");

		return service.getResult(userId);
	}

	@PostMapping("/api/final-result")
	public String saveFinalResult(
		HttpServletRequest request,
		@RequestBody FinalResultRequest finalResultRequest
	) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		service.saveFinalResult(finalResultRequest, userId);

		return userId;
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
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");

		return service.getPollingResult(userId);
	}
}
