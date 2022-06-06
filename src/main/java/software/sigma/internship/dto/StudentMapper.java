package software.sigma.internship.dto;

import org.springframework.stereotype.Service;
import software.sigma.internship.entity.Student;
@Service
public class StudentMapper {

    public Student toEntity(StudentDto dto) {
        Student entity = new Student();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setCourse(dto.getCourse());
        return entity;
    }

    public StudentDto toDto(Student entity) {
        StudentDto dto = new StudentDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setCourse(entity.getCourse());
        return dto;
    }
}
