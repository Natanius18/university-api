package software.sigma.internship.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import software.sigma.internship.dto.PersonDto;
import software.sigma.internship.entity.Person;
import software.sigma.internship.enums.Role;
import software.sigma.internship.enums.Status;
import software.sigma.internship.mapper.PersonDtoMapper;
import software.sigma.internship.repo.PersonRepository;
import software.sigma.internship.service.PersonService;
import software.sigma.internship.validator.exception.UserExistsWithEmailException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static software.sigma.internship.enums.Role.USER;
import static software.sigma.internship.enums.Status.ACTIVE;
import static software.sigma.internship.enums.Status.INACTIVE;


@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonDtoMapper personDtoMapper;
    private final BCryptPasswordEncoder encoder;
    private final Random random = new Random();

    private final KafkaTemplate<String, Map<String, String>> kafkaTemplate;

    @Override
    public PersonDto approve(String email, Role role) {
        Person person = personRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        person.setRole(role);
        return personDtoMapper.map(personRepository.save(person));
    }

    @Override
    public boolean verify(String verificationCode) {
        Person user = personRepository.findByVerificationCode(verificationCode).orElse(null);

        if (user == null || user.getStatus().equals(ACTIVE)) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setStatus(ACTIVE);
            personRepository.save(user);
            return true;
        }
    }

    @Override
    public void preparePersonForSave(PersonDto personDto, Long id) {
        String email = personDto.getEmail();
        if (id == null) {
            if (personRepository.existsByEmail(email)) {
                throw new UserExistsWithEmailException(email);
            }
            sendConfirmationEmail(personDto, email);
        }
        setRoleAndStatus(personDto, id);
        personDto.setPassword(encoder.encode(personDto.getPassword()));
    }

    private void setRoleAndStatus(PersonDto personDto, Long id) {
        Role role = USER;
        Status status = INACTIVE;
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
