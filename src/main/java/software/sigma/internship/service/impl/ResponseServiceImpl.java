package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.AnswerDto;
import software.sigma.internship.dto.ResponseDto;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.dto.TestStatisticsDto;
import software.sigma.internship.entity.Answer;
import software.sigma.internship.entity.Response;
import software.sigma.internship.entity.Student;
import software.sigma.internship.entity.Test;
import software.sigma.internship.repo.AnswerRepository;
import software.sigma.internship.repo.ResponseRepository;
import software.sigma.internship.repo.StudentRepository;
import software.sigma.internship.repo.TestRepository;
import software.sigma.internship.service.ResponseService;
import software.sigma.internship.service.TestStatisticsService;
import software.sigma.internship.test.passing.ScoreCounter;
import software.sigma.internship.validator.exception.AnswerNotFoundException;
import software.sigma.internship.validator.exception.TestNotFoundException;
import software.sigma.internship.validator.exception.UserNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResponseServiceImpl implements ResponseService {
    private final ResponseRepository responseRepository;
    private final AnswerRepository answerRepository;
    private final StudentRepository studentRepository;
    private final TestStatisticsService testStatisticsService;
    private final TestRepository testRepository;
    private final ModelMapper responseToStatisticsMapper;
    private final ModelMapper allResponsesMapper;
    private final ScoreCounter scoreCounter;

    @Override
    public ResponseDto findById(Long id) {
        Response response = responseRepository.findById(id).orElseThrow(() -> new TestNotFoundException(id));
        return responseToStatisticsMapper.map(response, ResponseDto.class);
    }

    @Override
    public List<ResponseDto> findByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new UserNotFoundException(studentId));
        List<Response> responses = student.getResponses();
        return getResponseDtoList(responses);
    }

    private List<ResponseDto> getResponseDtoList(List<Response> responses) {
        return responses.stream()
                .map(response -> allResponsesMapper.map(response, ResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDto save(ResponseDto response) {
        Long testId = response.getTest().getId();
        Test test = testRepository.findById(testId).orElseThrow(() -> new TestNotFoundException(testId));
        response.setTest(responseToStatisticsMapper.map(test, TestDto.class));

        response.setNumberOfTry(getNumberOfNewTry(response));
        response.setAnswers(getAnswerDtoList(response));

        response.setResult(scoreCounter.countResult(response));

        testStatisticsService.save(responseToStatisticsMapper.map(response, TestStatisticsDto.class));

        Response newResponse = responseRepository.save(responseToStatisticsMapper.map(response, Response.class));
        return responseToStatisticsMapper.map(newResponse, ResponseDto.class);
    }

    private List<AnswerDto> getAnswerDtoList(ResponseDto response) {
        return response.getAnswers()
                .stream()
                .map(answerDto -> {
                    Long answerDtoId = answerDto.getId();
                    Answer answer = answerRepository.findById(answerDtoId)
                            .orElseThrow(() -> new AnswerNotFoundException(answerDtoId));
                    return responseToStatisticsMapper.map(answer, AnswerDto.class);
                })
                .collect(Collectors.toList());
    }

    private int getNumberOfNewTry(ResponseDto response) {
        return responseRepository
                .getFirstByStudentIdAndTestOrderByNumberOfTryDesc(
                        response.getStudent().getId(),
                        responseToStatisticsMapper.map(response.getTest(), Test.class))
                .orElse(new Response())
                .getNumberOfTry() + 1;
    }
}
