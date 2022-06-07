package software.sigma.internship.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.sigma.internship.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}