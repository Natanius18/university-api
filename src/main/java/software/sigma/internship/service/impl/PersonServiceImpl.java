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

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper mapper;

    @Override
    public PersonDto approve(String email, Role role) {
        Person person = personRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        person.setRole(role);
        return mapper.map(personRepository.save(person), PersonDto.class);
    }
}
