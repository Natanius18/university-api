package software.sigma.internship.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.PersonDto;
import software.sigma.internship.entity.Person;
import software.sigma.internship.enums.Role;
import software.sigma.internship.enums.Status;
import software.sigma.internship.repo.PersonRepository;
import software.sigma.internship.service.PersonService;
import software.sigma.internship.validator.exception.UserExistsWithEmailException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * Implementation of {@link PersonService}.
 *
 * @author natanius
 */
@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder encoder;
    private final Random random = new Random();

    private final KafkaTemplate<String, Map<String, String>> kafkaTemplate;

    public PersonServiceImpl(PersonRepository personRepository, ModelMapper mapper, BCryptPasswordEncoder encoder, KafkaTemplate<String, Map<String, String>> kafkaTemplate) {
        this.personRepository = personRepository;
        this.mapper = mapper;
        this.encoder = encoder;
        this.kafkaTemplate = kafkaTemplate;
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

    /**
     * Verify email of the new user.
     *
     * @param verificationCode code which was sent by email to the user.
     * @return true if the code is valid, false otherwise.
     */
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

    /**
     * All necessary setup for the new user before save it to the database.
     *
     * @param personDto DTO of the user to be saved or updated.
     * @param id        id of the user to be saved (if null) or updated.
     */
    @Override
    public void preparePersonForSave(PersonDto personDto, Long id) {
        String email = personDto.getEmail();
        if (id == null && personRepository.existsByEmail(email)) {
            throw new UserExistsWithEmailException(email);
        }
        if (id == null) {
            sendConfirmationEmail(personDto, email);
        }
        setRoleAndStatus(personDto, id);
        personDto.setPassword(encoder.encode(personDto.getPassword()));
    }

    private void setRoleAndStatus(PersonDto personDto, Long id) {
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
    }

    private void sendConfirmationEmail(PersonDto personDto, String email) {
        String verificationCode = String.valueOf(random.nextInt(90_000) + 10_000);
        Map<String, String> map = new HashMap<>();
        map.put("secretPass", verificationCode);
        map.put("name", personDto.getFirstName() + " " + personDto.getLastName());
        map.put("receiver", email);
        kafkaTemplate.send("messages", map);
        personDto.setVerificationCode(verificationCode);
    }
}
