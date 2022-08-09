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

    /**
     * Verify email of the new user.
     * @param verificationCode code which was sent by email to the user.
     * @return true if the code is valid, false otherwise.
     */
    boolean verify(String verificationCode);

    /**
     * All necessary setup for the new user before save it to the database.
     * @param personDto DTO of the user to be saved or updated.
     * @param id id of the user to be saved (if null) or updated.
     */
    void preparePersonForSave(PersonDto personDto, Long id);
}
