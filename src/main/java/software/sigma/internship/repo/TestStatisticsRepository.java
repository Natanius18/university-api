package software.sigma.internship.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import software.sigma.internship.entity.TestStatistics;

@Repository
public interface TestStatisticsRepository extends MongoRepository<TestStatistics, String> {
}
