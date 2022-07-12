package software.sigma.internship.service;

import software.sigma.internship.dto.PersonDto;
import software.sigma.internship.enums.Role;

public interface PersonService {
    PersonDto approve(String email, Role role);
}
