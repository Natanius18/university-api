package software.sigma.internship.dao;

import software.sigma.internship.user.Student;

import java.util.List;

public interface StudentDao {
    List<Student> findAll();

    Student read(Long id);

    Student create(Student student);

    Student update(Long id, Student student);

    void delete(Long id);
}
