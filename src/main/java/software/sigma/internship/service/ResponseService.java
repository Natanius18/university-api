package software.sigma.internship.service;

import software.sigma.internship.dto.ResponseDto;

import java.util.List;

public interface ResponseService {
    ResponseDto findById(Long id);

    List<ResponseDto> findByStudent(Long id);

    ResponseDto save(ResponseDto response);

    void deleteById(Long id);
}
