package software.sigma.internship.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import software.sigma.internship.dto.QuestionDto;
import software.sigma.internship.entity.Question;

import static org.springframework.util.CollectionUtils.isEmpty;

@Mapper(uses = AnswerMapper.class)
public interface QuestionMapper {

    @Named("mapQuestionForStudents")
    @Mapping(target = "answers", qualifiedByName = "mapAnswerForStudents")
    QuestionDto mapQuestionForStudents(Question question);

    QuestionDto map(Question question);
    Question map(QuestionDto questionDto);

    @AfterMapping
    default void setInverseReferences(@MappingTarget Question question) {
        var answers = question.getAnswers();
        if (!isEmpty(answers)) {
            answers.forEach(answer -> answer.setQuestion(question));
        }
    }
}
