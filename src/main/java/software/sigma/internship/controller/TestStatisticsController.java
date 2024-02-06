package software.sigma.internship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.dto.TestStatisticsDto;
import software.sigma.internship.service.TestStatisticsService;

import java.util.List;
import java.util.Map;

import static software.sigma.internship.mongo.filters.core.FilterCollector.collectRestApiParams;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/statistics", produces = "application/json")
public class TestStatisticsController {

    private final TestStatisticsService statisticsService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('students:read', 'teachers:read')")
    public List<TestStatisticsDto> find(@RequestParam Map<String, String> filters) {
        Map<String, String> restApiQueries = collectRestApiParams(filters);
        return statisticsService.findAll(restApiQueries);
    }
}
