package be.pxl.activity.exception;

// Eis van de opdracht: eigen UNCHECKED exception (extends RuntimeException) -> 403
public class ForbiddenActionException extends RuntimeException {
    public ForbiddenActionException(String message) {
        super(message);
    }
}

