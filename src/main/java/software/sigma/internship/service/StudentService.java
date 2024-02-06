package software.sigma.internship.service;

import software.sigma.internship.dto.StudentDto;

import java.util.List;

public interface StudentService {

    List<StudentDto> findAll();

    StudentDto findById(Long id);

    StudentDto save(StudentDto student);

    void deleteById(Long id);
}
