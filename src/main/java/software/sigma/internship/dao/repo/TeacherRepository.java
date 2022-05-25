package software.sigma.internship.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.sigma.internship.user.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
