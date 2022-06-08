package software.sigma.internship.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.entity.Question;
import software.sigma.internship.entity.Test;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TestMapper {
    private final QuestionMapper questionMapper;

    public Test toEntity(TestDto dto) {
        Test entity = new Test();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setTeacher(dto.getTeacher());
        List<Question> questions = dto.getQuestions()
                .stream()
                .map(questionDto -> {
                    Question question = questionMapper.toEntity(questionDto);
                    question.setTest(entity);
                    return question;
                }).collect(Collectors.toList());
        entity.setQuestions(questions);
        return entity;
    }

    public TestDto toDto(Test entity) {
        TestDto dto = new TestDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        entity.getTeacher().setTests(null);
        dto.setTeacher(entity.getTeacher());
        dto.setQuestions(entity.getQuestions()
                .stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }
}