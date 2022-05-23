package software.sigma.internship.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.sigma.internship.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
