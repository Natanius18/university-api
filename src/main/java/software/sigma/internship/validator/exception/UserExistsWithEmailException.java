package software.sigma.internship.validator.exception;

public class UserExistsWithEmailException extends RuntimeException {
    public UserExistsWithEmailException(String email) {
        super("User with email " + email + " already exists");
    }
}