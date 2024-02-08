package software.sigma.internship.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import software.sigma.internship.dto.PersonDto;
import software.sigma.internship.entity.Person;

@Mapper
public interface PersonDtoMapper {

    @Mapping(target = "password", ignore = true)
    PersonDto map(Person person);

}
