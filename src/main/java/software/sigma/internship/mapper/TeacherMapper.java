package software.sigma.internship.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import software.sigma.internship.dto.TeacherDto;
import software.sigma.internship.entity.Teacher;

@Mapper
public interface TeacherMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "verificationCode", ignore = true)
    TeacherDto map(Teacher teacher);

    Teacher map(TeacherDto teacherDto);
}
