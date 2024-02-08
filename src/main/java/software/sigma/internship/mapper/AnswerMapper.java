package software.sigma.internship.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import software.sigma.internship.dto.AnswerDto;
import software.sigma.internship.entity.Answer;

@Mapper
public interface AnswerMapper {

    @Named("mapAnswerForStudents")
    @Mapping(target = "correct", ignore = true)
    AnswerDto mapAnswerForStudents(Answer answer);
    
    AnswerDto map(Answer answer);

    Answer map(AnswerDto answerDto);
}
