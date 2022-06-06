package software.sigma.internship.service;

import software.sigma.internship.dto.TeacherDto;

import java.util.List;

public interface TeacherService {
    List<TeacherDto> findAll();

    TeacherDto findById(Long id);

    TeacherDto save(TeacherDto teacher);

    void deleteById(Long id);
}
