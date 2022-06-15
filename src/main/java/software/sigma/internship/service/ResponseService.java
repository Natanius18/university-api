package software.sigma.internship.service;

import software.sigma.internship.dto.ResponseDto;

import java.util.List;

public interface ResponseService {
    List<ResponseDto> findAll();

    ResponseDto findById(Long id);

    ResponseDto save(ResponseDto response);

    void deleteById(Long id);
}
