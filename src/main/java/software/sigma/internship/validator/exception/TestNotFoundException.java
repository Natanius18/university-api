package software.sigma.internship.validator.exception;

public class TestNotFoundException extends RuntimeException {
    public TestNotFoundException(Long id) {
        super("Test " + id + " not found");
    }
}
