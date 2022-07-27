package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.repo.QuestionRepository;
import software.sigma.internship.service.QuestionService;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Override
    public void deleteQuestionByIdIn(List<Long> idList) {
        questionRepository.deleteQuestionByIdIn(idList);
    }
}
