package software.sigma.internship.service;

import software.sigma.internship.dto.TestStatisticsDto;

import java.util.List;
import java.util.Map;

public interface TestStatisticsService {
    TestStatisticsDto save(TestStatisticsDto testStatisticsDto);

    List<TestStatisticsDto> findAll(Map<String, String> restApiQueries);
}
