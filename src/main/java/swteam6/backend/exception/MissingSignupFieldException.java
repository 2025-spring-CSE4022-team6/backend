package swteam6.backend.exception;

public class MissingSignupFieldException extends RuntimeException {
    public MissingSignupFieldException(String message) {
        super(message);
    }
}
