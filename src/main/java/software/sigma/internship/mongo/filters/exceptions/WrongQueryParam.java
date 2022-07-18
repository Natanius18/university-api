package software.sigma.internship.mongo.filters.exceptions;

public class WrongQueryParam extends RuntimeException {

    public WrongQueryParam() {
    }

    public WrongQueryParam(String message) {
        super(message);
    }

}
