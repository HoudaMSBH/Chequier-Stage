package com.example.Stage.controller;

import com.example.Stage.dto.DashboardStatsResponse;
import com.example.Stage.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/stats")
    public DashboardStatsResponse getDashboardStats() {
        return dashboardService.getStats();
    }
}
