package software.sigma.internship.scheduler;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.TestStatisticsDto;
import software.sigma.internship.entity.TestStatistics;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
@AllArgsConstructor
public class ScheduledUtil {

    private final MongoTemplate mongoTemplate;
    private final KafkaTemplate<String, Map<String, List<TestStatisticsDto>>> kafkaTemplate;

    @Scheduled(cron = "0 0 18 ? * *")
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

    @Scheduled(cron = "0 0 0 ? * SUN")
    @CacheEvict(cacheNames = {"tests", "testsByTeacher", "allTests"}, allEntries = true)
    public void evictCaches() {
    }
}
