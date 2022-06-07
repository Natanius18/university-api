package software.sigma.internship.dto;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.internship.entity.Teacher;
import software.sigma.internship.entity.Test;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeacherMapper {
    private final TestMapper testMapper;

    public Teacher toEntity(TeacherDto dto) {
        Teacher entity = new Teacher();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPosition(dto.getPosition());
        List<Test> testList = dto.getTests().stream().map(testMapper::toEntity).collect(Collectors.toList());
        entity.setTests(testList);
        return entity;
    }

    public TeacherDto toDto(Teacher entity) {
        TeacherDto dto = new TeacherDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPosition(entity.getPosition());
        List<TestDto> testList = entity.getTests().stream().map(entity1 -> {
            TestDto testDto = testMapper.toDto(entity1);
            testDto.setTeacher(null);
            testDto.setQuestions(null);
            return testDto;
        }).collect(Collectors.toList());
        dto.setTests(testList);
        return dto;
    }
}
