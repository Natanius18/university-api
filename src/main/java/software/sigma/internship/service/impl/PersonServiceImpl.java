package software.sigma.internship.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.PersonDto;
import software.sigma.internship.entity.Person;
import software.sigma.internship.enums.Role;
import software.sigma.internship.repo.PersonRepository;
import software.sigma.internship.service.PersonService;

/**
 * Implementation of {@link PersonService}.
 * @author natanius
 */
@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper mapper;

    /**
     * Set a role to the person by email.
     * @param email the email of the person.
     * @param role the role to be assigned to the person.
     * @return person with the new role assigned.
     */
    @Override
    public PersonDto approve(String email, Role role) {
        Person person = personRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        person.setRole(role);
        return mapper.map(personRepository.save(person), PersonDto.class);
    }
}
