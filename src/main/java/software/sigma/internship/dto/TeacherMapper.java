package software.sigma.internship.dto;

import org.springframework.stereotype.Service;
import software.sigma.internship.entity.Teacher;

@Service
public class TeacherMapper {

    public Teacher toEntity(TeacherDto dto) {
        Teacher entity = new Teacher();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPosition(dto.getPosition());
        return entity;
    }

    public TeacherDto toDto(Teacher entity) {
        TeacherDto dto = new TeacherDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPosition(entity.getPosition());
        return dto;
    }
}
