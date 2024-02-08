package software.sigma.internship.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import software.sigma.internship.dto.StudentDto;
import software.sigma.internship.entity.Student;

@Mapper
public interface StudentMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    StudentDto map(Student student);

    Student map(StudentDto teacherDto);
}
