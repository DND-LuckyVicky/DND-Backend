package com.luckyvicky.dndbackend.ys;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luckyvicky.dndbackend.dto.PollingResult;
import com.luckyvicky.dndbackend.dto.SelectedRandomGotcha;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class YsController {
	private final YsService ysService;

	@PostMapping("/api/final-result")
	public String saveFinalResult(
		HttpServletRequest request,
		@RequestBody Long statementId
	) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");

		ysService.saveFinalResult(statementId, userId);

		return userId;
	}

	@GetMapping("/api/final-result/{userId}")
	public FinalResults getFinalResult(
		@PathVariable String userId
	) {
		return ysService.getFinalResult(userId);
	}

	@PostMapping("/api/random-gotcha")
	public String selectRandomGotcha(
		@RequestBody SelectedRandomGotcha selectedRandomGotcha
	) {
		ysService.selectRandomGotcha(selectedRandomGotcha);

		return selectedRandomGotcha.userId();
	}

	@GetMapping("/api/final-result/polling")
	public PollingResult getPollingResult(
		HttpServletRequest request
	) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute("userId");

		return ysService.getPollingResult(userId);
	}

}
