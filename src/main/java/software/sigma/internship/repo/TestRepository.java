package software.sigma.internship.repo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import software.sigma.internship.entity.Test;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    @Override
    @Cacheable("tests")
    Optional<Test> findById(@NonNull Long id);
}
