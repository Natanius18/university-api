package software.sigma.internship.mapper;

import org.mapstruct.Mapper;
import software.sigma.internship.dto.TestStatisticsDto;
import software.sigma.internship.entity.TestStatistics;

@Mapper
public interface TestStatisticsMapper {

    TestStatistics map(TestStatisticsDto testStatisticsDto);

    TestStatisticsDto map(TestStatistics testStatisticsDto);

}
