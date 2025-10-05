package com.example.poststats.web;

import com.example.poststats.statistics.PostStatisticsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "http://localhost:3000")
public class StatisticsController {

    private final PostStatisticsService statisticsService;

    public StatisticsController(PostStatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public StatisticsResponse getStatistics() {
        return StatisticsResponse.fromSnapshot(statisticsService.getSnapshot());
    }
}
