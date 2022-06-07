package software.sigma.internship.validator.exception;

public class AnswerNotFoundException extends RuntimeException {
    public AnswerNotFoundException(Long id) {
        super("Answer " + id + " not found");
    }
}
