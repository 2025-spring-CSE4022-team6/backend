package swteam6.backend.exception;

public class PlaceNotFoundException extends RuntimeException{
    public PlaceNotFoundException(String message) {
        super(message);
    }
}
