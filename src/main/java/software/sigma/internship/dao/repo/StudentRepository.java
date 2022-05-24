package software.sigma.internship.dao.repo;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import software.sigma.internship.user.Student;

import java.util.List;


public interface StudentRepository {
    @SqlQuery("SELECT * FROM student")
    @RegisterBeanMapper(Student.class)
    List<Student> selectAll();
}
