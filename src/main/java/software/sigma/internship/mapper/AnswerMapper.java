package software.sigma.internship.mapper;

import org.springframework.stereotype.Service;
import software.sigma.internship.dto.AnswerDto;
import software.sigma.internship.entity.Answer;

@Service
public class AnswerMapper {
    public Answer toEntity(AnswerDto dto) {
        Answer entity = new Answer();
        entity.setId(dto.getId());
        entity.setCorrect(dto.isCorrect());
        entity.setOption(dto.getOption());
        return entity;
    }

    public AnswerDto toDto(Answer entity) {
        AnswerDto dto = new AnswerDto();
        dto.setId(entity.getId());
        dto.setCorrect(entity.isCorrect());
        dto.setOption(entity.getOption());
        return dto;
    }
}