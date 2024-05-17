package com.luckyvicky.dndbackend.ys;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luckyvicky.dndbackend.dto.Destination;
import com.luckyvicky.dndbackend.dto.MapPosition;
import com.luckyvicky.dndbackend.dto.PollingResult;
import com.luckyvicky.dndbackend.dto.Results;
import com.luckyvicky.dndbackend.dto.SelectedDestination;
import com.luckyvicky.dndbackend.dto.SelectedRandomGotcha;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class YsController {
	private final YsService ysService;

	@PostMapping("")
	public List<Destination> saveDestination(
		HttpServletRequest request,
		@RequestBody MapPosition mapPosition
	) {
		HttpSession session = request.getSession();
		String userId = request.getRemoteAddr();
		session.setAttribute("userId", userId);

		return ysService.saveDestination(mapPosition, userId);
	}

	@PostMapping("")
	public String selectDestination(
		HttpServletRequest request,
		@RequestBody SelectedDestination selectedDestination
	) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");
		ysService.selectDestination(userId);

		return userId;
	}

	@GetMapping("")
	public Results getResult(
		HttpServletRequest request
	) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");

		return ysService.getResult(userId);
	}

	@PostMapping("")
	public String saveFinalResult(
		HttpServletRequest request,
		@RequestBody Long statementId
	) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");

		ysService.saveFinalResult(statementId, userId);

		return userId;
	}

	@GetMapping("/{userId}")
	public FinalResults getFinalResult(
		@PathVariable String userId
	) {
		return ysService.getFinalResult(userId);
	}

	@PostMapping("")
	public String selectRandomGotcha(
		@RequestBody SelectedRandomGotcha selectedRandomGotcha
	) {
		ysService.selectRandomGotcha(selectedRandomGotcha);

		return selectedRandomGotcha.userId();
	}

	@GetMapping("")
	public PollingResult getPollingResult(
		HttpServletRequest request
	) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");

		return ysService.getPollingResult(userId);
	}

}
