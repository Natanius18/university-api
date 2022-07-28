package software.sigma.internship.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.sigma.internship.entity.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByEmail(String email);
    Optional<Person> findByEmail(String email);
    Optional<Person> findByVerificationCode(String verificationCode);
}