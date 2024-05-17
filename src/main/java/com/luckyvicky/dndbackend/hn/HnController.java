package com.luckyvicky.dndbackend.hn;

import com.luckyvicky.dndbackend.dto.Destination;
import com.luckyvicky.dndbackend.dto.MapPosition;
import com.luckyvicky.dndbackend.dto.Results;
import com.luckyvicky.dndbackend.dto.SelectedDestination;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HnController {
    private final HnService hnService;

    @PostMapping("/api/destination")
    public List<Destination> saveDestination(
        HttpServletRequest request,
        @RequestBody MapPosition mapPosition
    ) {
        HttpSession session = request.getSession();
        String userId = request.getRemoteAddr();
        session.setAttribute("userId", userId);

        return hnService.saveDestination(mapPosition, userId);
    }


    @PostMapping("/api/destination/select")
    public String selectDestination(
        HttpServletRequest request,
        @RequestBody SelectedDestination selectedDestination
    ) {
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        hnService.selectDestination(userId);

        return userId;
    }

    @GetMapping("/api/result")
    public Results getResult(
        HttpServletRequest request
    ) {
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");

        return hnService.getResult(userId);
    }
}
