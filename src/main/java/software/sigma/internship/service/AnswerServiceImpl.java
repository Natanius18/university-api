package software.sigma.internship.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.AnswerDto;
import software.sigma.internship.entity.Answer;
import software.sigma.internship.mapper.AnswerMapper;
import software.sigma.internship.repo.AnswerRepository;
import software.sigma.internship.validator.exception.AnswerNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    AnswerRepository answerRepository;
    AnswerMapper answerMapper;
    @Override
    public List<AnswerDto> findAll() {
        List<Answer> tests = answerRepository.findAll();
        return tests.stream()
                .map(entity -> answerMapper.toDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public AnswerDto findById(Long id) {
        Answer answer = answerRepository.findById(id).orElseThrow(() -> new AnswerNotFoundException(id));
        return answerMapper.toDto(answer);
    }

    @Override
    public AnswerDto save(AnswerDto answerDto) {
        Answer answer = answerRepository.save(answerMapper.toEntity(answerDto));
        return answerMapper.toDto(answer);
    }

    @Override
    public void deleteById(Long id) {
        answerRepository.deleteById(id);
    }
}
