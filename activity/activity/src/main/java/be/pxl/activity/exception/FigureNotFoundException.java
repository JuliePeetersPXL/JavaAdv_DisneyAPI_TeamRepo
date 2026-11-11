package be.pxl.activity.exception;

// Geen exacte match van de naam in de Disney API -> 400 (zo staat het in de opdracht)
public class FigureNotFoundException extends InvalidInputException {
    public FigureNotFoundException(String figure) {
        super("No Disney figure found with exact name: " + figure);
    }
}
