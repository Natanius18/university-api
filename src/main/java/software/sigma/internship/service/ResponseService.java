package software.sigma.internship.service;

import software.sigma.internship.dto.ResponseDto;
import software.sigma.internship.dto.StudentDto;

import java.util.List;

public interface ResponseService {
    List<ResponseDto> findAll();

    ResponseDto findById(Long id);

    List<ResponseDto> findByStudent(StudentDto studentDto);

    ResponseDto save(ResponseDto response);

    void deleteById(Long id);
}
