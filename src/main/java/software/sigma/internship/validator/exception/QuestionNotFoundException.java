package software.sigma.internship.validator.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Long id) {
        super("Question " + id + " not found");
    }
}
