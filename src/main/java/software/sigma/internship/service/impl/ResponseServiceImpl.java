package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.AnswerDto;
import software.sigma.internship.dto.ResponseDto;
import software.sigma.internship.entity.Answer;
import software.sigma.internship.entity.Response;
import software.sigma.internship.repo.AnswerRepository;
import software.sigma.internship.repo.ResponseRepository;
import software.sigma.internship.service.ResponseService;
import software.sigma.internship.validator.exception.AnswerNotFoundException;
import software.sigma.internship.validator.exception.TestNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private final ResponseRepository repository;
    private final AnswerRepository answerRepository;
    private final ModelMapper mapper;

    @Override
    public List<ResponseDto> findAll() {
        List<Response> responses = repository.findAll();
        return responses.stream()
                .map(response -> {
                    ResponseDto responseDto = mapper.map(response, ResponseDto.class);
                    responseDto.setAnswers(null);
                    return responseDto;
                }).collect(Collectors.toList());
    }

    @Override
    public ResponseDto findById(Long id) {
        Response response = repository.findById(id).orElseThrow(() -> new TestNotFoundException(id));
        return mapper.map(response, ResponseDto.class);
    }

    @Override
    public ResponseDto save(ResponseDto response) {
        List<AnswerDto> answerDtos = response.getAnswers().stream()
                .map(answerDto -> {
                    Answer answer = answerRepository.findById(answerDto.getId())
                            .orElseThrow(() -> new AnswerNotFoundException(answerDto.getId()));
                    return mapper.map(answer, AnswerDto.class);
                })
                .collect(Collectors.toList());
        response.setAnswers(answerDtos);
        Response newResponse = repository.save(mapper.map(response, Response.class));
        return mapper.map(newResponse, ResponseDto.class);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
