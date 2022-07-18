package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.TestStatisticsDto;
import software.sigma.internship.entity.TestStatistics;
import software.sigma.internship.repo.TestStatisticsRepository;
import software.sigma.internship.service.TestStatisticsService;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static software.sigma.internship.mongo.filters.core.FilterApplier.applyRestApiQueries;

@Service
@AllArgsConstructor
public class TestStatisticsServiceImpl implements TestStatisticsService {

    private final TestStatisticsRepository repository;
    private final ModelMapper mapper;
    private final MongoTemplate mongoTemplate;


    @Override
    public TestStatisticsDto save(TestStatisticsDto testStatisticsDto) {
        TestStatistics testStatistics = mapper.map(testStatisticsDto, TestStatistics.class);
        testStatistics.setDate(new Date());
        return mapper.map(repository.save(testStatistics), TestStatisticsDto.class);
    }

    @Override
    public List<TestStatisticsDto> findAll(Map<String, String> restApiQueries) {
        Aggregation aggregation = newAggregation(applyRestApiQueries(restApiQueries));
        AggregationResults<TestStatisticsDto> results = mongoTemplate.aggregate(aggregation, TestStatistics.class, TestStatisticsDto.class);
        return results.getMappedResults();
    }
}
