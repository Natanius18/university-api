package software.sigma.internship.service;

import software.sigma.internship.user.Student;

import java.util.List;

public interface StudentService {
    List<Student> readAll();

    Student read(Long id);

    Student createStudent(Student student);

    Student updateStudent(Long id, Student student);

    void deleteStudent(long id);
}
