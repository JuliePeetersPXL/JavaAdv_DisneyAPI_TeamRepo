package be.pxl.activity.exception;

// Basisklasse voor alle fouten van de client -> HTTP 400
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}

