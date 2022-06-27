package software.sigma.internship.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.sigma.internship.entity.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}
