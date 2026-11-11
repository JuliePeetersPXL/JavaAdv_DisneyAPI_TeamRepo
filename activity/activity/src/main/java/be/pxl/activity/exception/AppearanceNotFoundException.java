package be.pxl.activity.exception;

// Geen appearance met dit id -> 404
public class AppearanceNotFoundException extends RuntimeException {
    public AppearanceNotFoundException(Long id) {
        super("Appearance not found: " + id);
    }
}

