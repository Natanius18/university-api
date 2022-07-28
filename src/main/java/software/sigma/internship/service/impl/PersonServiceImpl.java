package software.sigma.internship.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import software.sigma.internship.dto.PersonDto;
import software.sigma.internship.entity.Person;
import software.sigma.internship.enums.Role;
import software.sigma.internship.enums.Status;
import software.sigma.internship.repo.PersonRepository;
import software.sigma.internship.service.PersonService;
import software.sigma.internship.validator.exception.UserExistsWithEmailException;

import java.util.Optional;
import java.util.Random;

/**
 * Implementation of {@link PersonService}.
 *
 * @author natanius
 */
@Service
public class PersonServiceImpl implements PersonService {
    @Value("${email.server}")
    private String emailServerRoot;
    private final PersonRepository personRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder encoder;
    private final Random random = new Random();

    public PersonServiceImpl(PersonRepository personRepository, ModelMapper mapper, BCryptPasswordEncoder encoder) {
        this.personRepository = personRepository;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    /**
     * Set a role to the person by email.
     *
     * @param email the email of the person.
     * @param role  the role to be assigned to the person.
     * @return person with the new role assigned.
     */
    @Override
    public PersonDto approve(String email, Role role) {
        Person person = personRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        person.setRole(role);
        return mapper.map(personRepository.save(person), PersonDto.class);
    }

    @Override
    public boolean verify(String verificationCode) {
        Person user = personRepository.findByVerificationCode(verificationCode).orElse(null);

        if (user == null || user.getStatus().equals(Status.ACTIVE)) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setStatus(Status.ACTIVE);
            personRepository.save(user);
            return true;
        }
    }

    @Override
    public void preparePersonForSave(PersonDto personDto, Long id) {
        String email = personDto.getEmail();
        if (id == null && personRepository.existsByEmail(email)) {
            throw new UserExistsWithEmailException(email);
        }
        if (id == null) {
            String verificationCode = String.valueOf(random.nextInt(90_000) + 10_000);
            String uri = "http://" + emailServerRoot +"/emails/v1/send/confirmation?" +
                    "receiver=" + email +
                    "&firstName="+ personDto.getFirstName() +
                    "&lastName=" + personDto.getLastName() +
                    "&verificationCode=" + verificationCode;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForEntity(uri, String.class);
            personDto.setVerificationCode(verificationCode);
        }
        Role role = Role.USER;
        Status status = Status.INACTIVE;
        if (id != null) {
            Optional<Person> existingPerson = personRepository.findById(id);
            if (existingPerson.isPresent()) {
                role = existingPerson.get().getRole();
                status = existingPerson.get().getStatus();
            }
        }
        personDto.setRole(role);
        personDto.setStatus(status);
        personDto.setPassword(encoder.encode(personDto.getPassword()));
    }
}
