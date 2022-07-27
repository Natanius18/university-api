package software.sigma.internship.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.sigma.internship.entity.Response;
import software.sigma.internship.entity.Test;

import java.util.Optional;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    Optional<Response> getFirstByStudentIdAndTestOrderByNumberOfTryDesc(Long studentId, Test test);
}