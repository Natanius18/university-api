package software.sigma.internship.service;

import software.sigma.internship.user.Student;

import java.util.List;

public interface StudentService {
    List<Student> readAll();

    Student read(Long id);

    Student create(Student student);

    Student update(Long id, Student student);

    void delete(Long id);
}
