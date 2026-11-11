package be.pxl.activity.exception;

// De Disney API antwoordt niet (geen fout van de gebruiker) -> 502 Bad Gateway
public class DisneyApiUnavailableException extends RuntimeException {
    public DisneyApiUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }

