package software.sigma.internship.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.AnswerDto;
import software.sigma.internship.dto.ResponseDto;
import software.sigma.internship.entity.Response;
import software.sigma.internship.entity.Test;
import software.sigma.internship.mapper.AnswerMapper;
import software.sigma.internship.mapper.ResponseMapper;
import software.sigma.internship.mapper.TestMapper;
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

@Service
@RequiredArgsConstructor
public class ResponseServiceImpl implements ResponseService {

    private final ResponseRepository responseRepository;
    private final AnswerRepository answerRepository;
    private final StudentRepository studentRepository;
    private final TestStatisticsService testStatisticsService;
    private final TestRepository testRepository;
    private final ResponseMapper responseMapper;
    private final TestMapper testMapper;
    private final AnswerMapper answerMapper;
    private final ScoreCounter scoreCounter;

    @Override
    public ResponseDto findById(Long id) {
        Response response = responseRepository.findById(id).orElseThrow(() -> new TestNotFoundException(id));
        return responseMapper.mapToResponseDto(response);
    }

    @Override
    public List<ResponseDto> findByStudent(Long studentId) {
        return studentRepository.findById(studentId)
            .orElseThrow(() -> new UserNotFoundException(studentId))
            .getResponses()
            .stream()
            .map(responseMapper::mapForList)
            .toList();
    }

    @Override
    public ResponseDto save(ResponseDto responseDto) {
        Long testId = responseDto.getTest().getId();
        Test test = testRepository.findById(testId).orElseThrow(() -> new TestNotFoundException(testId));
        responseDto.setTest(testMapper.mapForTeacher(test));

        responseDto.setNumberOfTry(getNumberOfNewTry(responseDto));
        responseDto.setAnswers(getAnswerDtoList(responseDto));

        responseDto.setResult(scoreCounter.countResult(responseDto));

        testStatisticsService.save(responseMapper.mapToTestStatisticsDto(responseDto));

        Response newResponse = responseRepository.save(responseMapper.mapToResponse(responseDto));
        return responseMapper.mapToResponseDto(newResponse);
    }

    private List<AnswerDto> getAnswerDtoList(ResponseDto response) {
        return response.getAnswers()
            .stream()
            .map(answerDto -> {
                Long answerDtoId = answerDto.getId();
                return answerRepository.findById(answerDtoId)
                    .map(answerMapper::map)
                    .orElseThrow(() -> new AnswerNotFoundException(answerDtoId));
                }).toList();
    }

    private int getNumberOfNewTry(ResponseDto response) {
        return responseRepository.getFirstByStudentIdAndTestOrderByNumberOfTryDesc(
                response.getStudent().getId(),
                testMapper.map(response.getTest()))
            .orElse(new Response())
            .getNumberOfTry() + 1;
    }
}
