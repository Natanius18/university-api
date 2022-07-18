package software.sigma.internship.controller;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.internship.dto.TestStatisticsDto;
import software.sigma.internship.service.TestStatisticsService;

import java.util.List;
import java.util.Map;

import static software.sigma.internship.mongo.filters.core.RestFullAPI.collectRestApiParams;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/statistics", produces = "application/json")
public class TestStatisticsController {
    private final TestStatisticsService statisticsService;

    @GetMapping
    public List<TestStatisticsDto> find(@ApiParam(hidden = true)
                                        @RequestParam Map<String, String> filters,
                                        @ApiParam(value = "field to order by", example = "testName")
                                        String sort,
                                        @ApiParam(value = "field to set filter(s)", example = "{\"testName\":\"test 1\"}")
                                        String q,
                                        @ApiParam(value = "amount of records on the page", example = "2")
                                        String pageSize,
                                        @ApiParam(value = "number of the page", example = "2")
                                        String page,
                                        @ApiParam(value = "shows average mark for each test if true", example = "true")
                                        String average,
                                        @ApiParam(value = "select fields to show", example = "testName,result")
                                        String select) {
        Map<String, String> restApiQueries = collectRestApiParams(filters);
        return statisticsService.findAll(restApiQueries);
    }
}
