package software.sigma.internship.service;

import software.sigma.internship.entity.Question;

import java.util.List;

public interface QuestionService {
    List<Question> findAll();

    Question findById(Long id);

    Question save(Question question);

    void deleteById(Long id);
}
