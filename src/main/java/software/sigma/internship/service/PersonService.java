package software.sigma.internship.service;

import software.sigma.internship.dto.PersonDto;
import software.sigma.internship.enums.Role;

/**
 * Interface for approving a role of a person.
 * @author natanius
 */

public interface PersonService {
    /**
     * Set a role to the person by email.
     * @param email the email of the person.
     * @param role the role to be assigned to the person.
     * @return person with the new role assigned.
     */
    PersonDto approve(String email, Role role);

    boolean verify(String verificationCode);

    void preparePersonForSave(PersonDto personDto, Long id);
}
