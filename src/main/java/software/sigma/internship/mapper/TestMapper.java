package software.sigma.internship.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import software.sigma.internship.dto.TestDto;
import software.sigma.internship.entity.Question;
import software.sigma.internship.entity.Test;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Mapper(uses = {TeacherMapper.class, QuestionMapper.class})
public interface TestMapper {

    @Mapping(target = "questions", qualifiedByName = "mapQuestionForStudents")
    TestDto mapForStudent(Test test);

    TestDto mapForTeacher(Test test);

    @Mapping(target = "questions", ignore = true)
    TestDto mapWithoutQuestions(Test test);

    Test map(TestDto test);

    @AfterMapping
    default void setInverseReferences(@MappingTarget Test test) {
        List<Question> questions = test.getQuestions();
        if (!isEmpty(questions)) {
            questions.forEach(question -> question.setTest(test));
        }
    }

}
