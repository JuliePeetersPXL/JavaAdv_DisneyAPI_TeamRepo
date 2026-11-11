package be.pxl.activity.exception;

// Erft van InvalidInputException, dus geeft automatisch ook 400
public class EmailAlreadyInUseException extends InvalidInputException {
    public EmailAlreadyInUseException(String email) {
        super("Email already in use: " + email);
    }
}
