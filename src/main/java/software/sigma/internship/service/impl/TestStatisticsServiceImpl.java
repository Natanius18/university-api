package software.sigma.internship.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.TestStatisticsDto;
import software.sigma.internship.entity.TestStatistics;
import software.sigma.internship.mapper.TestStatisticsMapper;
import software.sigma.internship.repo.TestStatisticsRepository;
import software.sigma.internship.service.TestStatisticsService;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static software.sigma.internship.mongo.filters.core.FilterApplier.applyRestApiQueries;

@Service
@RequiredArgsConstructor
public class TestStatisticsServiceImpl implements TestStatisticsService {

    private final TestStatisticsRepository repository;
    private final TestStatisticsMapper testStatisticsMapper;
    private final MongoTemplate mongoTemplate;


    @Override
    public TestStatisticsDto save(TestStatisticsDto testStatisticsDto) {
        var testStatistics = testStatisticsMapper.map(testStatisticsDto);
        testStatistics.setDate(new Date());
        return testStatisticsMapper.map(repository.save(testStatistics));
    }

    @Override
    public List<TestStatisticsDto> findAll(Map<String, String> restApiQueries) {
        var aggregation = newAggregation(applyRestApiQueries(restApiQueries));
        return mongoTemplate.aggregate(aggregation, TestStatistics.class, TestStatisticsDto.class)
            .getMappedResults();
    }
}
