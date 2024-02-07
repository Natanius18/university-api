package software.sigma.internship.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.TestStatisticsDto;
import software.sigma.internship.entity.TestStatistics;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.stream.Collectors.groupingBy;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

    private final MongoTemplate mongoTemplate;
    private final KafkaTemplate<String, Map<String, List<TestStatisticsDto>>> kafkaTemplate;

    @Scheduled(cron = "${cron.send.statistics}")
    public void sendStatistics() {
        var yesterday = Date.from(now().minus(1, DAYS));
        var operation = match(where("Date").gt(yesterday));
        var aggregation = newAggregation(operation);

        var listMap = mongoTemplate.aggregate(aggregation, TestStatistics.class, TestStatisticsDto.class)
            .getMappedResults()
            .stream()
            .collect(groupingBy(TestStatisticsDto::getTeacherEmail));

        kafkaTemplate.send("statistics", listMap);
    }

    @Scheduled(cron = "${cron.evict.caches}")
    @CacheEvict(cacheNames = {"tests", "testsByTeacher", "allTests"}, allEntries = true)
    public void evictCaches() {
        log.info("Evicting caches...");
    }
}
