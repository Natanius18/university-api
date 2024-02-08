package software.sigma.internship.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import software.sigma.internship.dto.ResponseDto;
import software.sigma.internship.dto.TestStatisticsDto;
import software.sigma.internship.entity.Response;

@Mapper(uses = {StudentMapper.class, AnswerMapper.class})
public interface ResponseMapper {

    @Mapping(source = "test.name", target = "testName")
    @Mapping(source = "test.teacher.email", target = "teacherEmail")
    TestStatisticsDto mapToTestStatisticsDto(ResponseDto responseDto);

    @Mapping(target = "test", ignore = true)
    @Mapping(target = "answers", ignore = true)
    ResponseDto mapToResponseDto(Response response);

    Response mapToResponse(ResponseDto responseDto);

    @Mapping(target = "test.questions", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "answers", qualifiedByName = "mapAnswerForStudents")
    ResponseDto mapForList(Response response);
}
