package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.sigma.internship.service.TestStatisticsService;
import software.sigma.internship.dto.TestStatisticsDto;
import software.sigma.internship.entity.TestStatistics;
import software.sigma.internship.repo.TestStatisticsRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static software.sigma.internship.mongo.filters.core.FilterApplier.applyRestApiQueries;

@Service
@AllArgsConstructor
public class TestStatisticsServiceImpl implements TestStatisticsService {

    private final TestStatisticsRepository repository;
    private final ModelMapper responseToStatisticsMapper;
    private final MongoTemplate mongoTemplate;
    private final KafkaTemplate<String, Map<String, List<TestStatisticsDto>>> kafkaTemplate;


    @Override
    public TestStatisticsDto save(TestStatisticsDto testStatisticsDto) {
        TestStatistics testStatistics = responseToStatisticsMapper.map(testStatisticsDto, TestStatistics.class);
        testStatistics.setDate(new Date());
        return responseToStatisticsMapper.map(repository.save(testStatistics), TestStatisticsDto.class);
    }

    @Override
    public List<TestStatisticsDto> findAll(Map<String, String> restApiQueries) {
        Aggregation aggregation = newAggregation(applyRestApiQueries(restApiQueries));
        AggregationResults<TestStatisticsDto> results = mongoTemplate.aggregate(aggregation, TestStatistics.class, TestStatisticsDto.class);
        return results.getMappedResults();
    }

    @Scheduled(cron = "0 31 18 1/1 * ?")
    private void sendStatistics() {
        Date yesterday = Date.from(Instant.now().minus(1, ChronoUnit.DAYS));
        MatchOperation operation = Aggregation.match(Criteria.where("Date").gt(yesterday));
        Aggregation aggregation = newAggregation(operation);
        AggregationResults<TestStatisticsDto> results = mongoTemplate.aggregate(aggregation, TestStatistics.class, TestStatisticsDto.class);
        List<TestStatisticsDto> mappedResults = results.getMappedResults();
        Map<String, List<TestStatisticsDto>> listMap =
                mappedResults.stream().collect(Collectors.groupingBy(TestStatisticsDto::getTeacherEmail));
        kafkaTemplate.send("statistics", listMap);
    }
}
