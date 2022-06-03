package software.sigma.internship.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.sigma.internship.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}