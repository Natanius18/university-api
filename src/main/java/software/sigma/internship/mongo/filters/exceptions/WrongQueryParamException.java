package software.sigma.internship.mongo.filters.exceptions;

public class WrongQueryParamException extends RuntimeException {

    public WrongQueryParamException() {
    }

    public WrongQueryParamException(String message) {
        super(message);
    }

}
