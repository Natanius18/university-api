package software.sigma.internship.repo;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import software.sigma.internship.entity.TestStatistics;

import java.util.List;

@Repository
public interface TestStatisticsRepository extends MongoRepository<TestStatistics, String> {

    @Aggregation(pipeline = {
            "{ $group : {_id: '$testName', result : { $avg : '$result' } } }",
            "{ $sort : { result: ?0 } }"
    })
    List<TestStatistics> findAvgResultForEachTest(String order);

}
