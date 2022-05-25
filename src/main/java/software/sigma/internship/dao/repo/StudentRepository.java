package software.sigma.internship.dao.repo;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import software.sigma.internship.user.Student;

import java.util.List;

public interface StudentRepository {
    @SqlQuery("SELECT * FROM student")
    @RegisterBeanMapper(Student.class)
    List<Student> selectAll();

    @SqlQuery("SELECT * FROM student WHERE id = :id")
    @RegisterBeanMapper(Student.class)
    Student selectById(@Bind("id") Long id);


    @SqlUpdate("INSERT INTO student (first_name, last_name, course) VALUES (:firstName, :lastName, :course)")
    int insert(@BindBean Student student);

    @SqlQuery("SELECT * FROM student WHERE first_name = :firstName AND last_name = :lastName LIMIT 1")
    @RegisterBeanMapper(Student.class)
    Student findByName(@Bind("firstName") String firstName, @Bind("lastName") String lastName);

    @SqlUpdate("UPDATE student SET first_name = :firstName, last_name = :lastName, course = :course WHERE id = :student_id")
    int update(@Bind("student_id") Long id, @BindBean Student student);

    @SqlUpdate("DELETE FROM student WHERE id = :student_id")
    void delete(@Bind("student_id") Long id);
}
