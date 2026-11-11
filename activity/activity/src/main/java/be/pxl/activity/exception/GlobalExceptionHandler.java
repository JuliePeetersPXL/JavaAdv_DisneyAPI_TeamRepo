package be.pxl.activity.exception;

// --- Imports: klassen van Spring die we hieronder gebruiken ---
import org.springframework.http.HttpStatus; // de statuscodes (400, 403, 404, 502, ...)
import org.springframework.http.ResponseEntity; // een volledig HTTP-antwoord: status + body
import org.springframework.http.converter.HttpMessageNotReadableException; // fout: Spring kan de JSON niet lezen
import org.springframework.web.bind.MethodArgumentNotValidException; // fout: @Valid-controle mislukt
import org.springframework.web.bind.annotation.ExceptionHandler; // koppelt een exceptie aan een methode
import org.springframework.web.bind.annotation.RestControllerAdvice; // maakt dit een "vangnet" voor alle controllers

import java.util.Map; // sleutel-waarde-paar, wordt in JSON {"error": "..."}
import java.util.stream.Collectors; // om meerdere foutmeldingen samen te voegen tot één tekst

// @RestControllerAdvice: deze klasse luistert mee met ALLE controllers.
// Gooit een controller (of een service die hij aanroept) een exceptie,
// dan zoekt Spring hier de passende @ExceptionHandler-methode.
// Zo hoef je in de controllers zelf geen try/catch te schrijven.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Wordt aangeroepen als een @Valid-controle op een @RequestBody mislukt,
    // bijvoorbeeld een @NotBlank-veld dat leeg is of een @Email met een fout
    // formaat.
    // Antwoord: 400 Bad Request (de client stuurde foute gegevens).
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        // getFieldErrors() = lijst van alle velden die niet in orde waren
        String message = ex.getBindingResult().getFieldErrors().stream()
                // maak van elke fout een tekst, bv. "email: must be a well-formed email
                // address"
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                // plak alle fouten aan elkaar, gescheiden door "; "
                .collect(Collectors.joining("; "));
        return error(HttpStatus.BAD_REQUEST, message);
    }

    // Wordt aangeroepen als Spring de JSON niet kan omzetten naar een Java-object:
    // kapotte JSON (vergeten komma, accolade) of een waarde in een fout formaat,
    // bv. een datum die niet als yyyy-MM-ddTHH:mm:ss geschreven is.
    // Antwoord: 400 Bad Request, met een vaste uitleg voor de gebruiker.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleUnreadable(HttpMessageNotReadableException ex) {
        return error(HttpStatus.BAD_REQUEST, "Malformed JSON or invalid value (date format: yyyy-MM-ddTHH:mm:ss)");
    }

    // Onze EIGEN exceptie voor foute invoer van de client.
    // Omdat EmailAlreadyInUseException en FigureNotFoundException
    // erven van InvalidInputException (extends), vangt deze ene handler die ook op.
    // Antwoord: 400 Bad Request, met de boodschap die we zelf in de exceptie
    // zetten.
    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Map<String, String>> handleInvalidInput(InvalidInputException ex) {
        return error(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // Eigen exceptie: de gevraagde appearance bestaat niet (bv. verkeerd id).
    // Antwoord: 404 Not Found.
    @ExceptionHandler(AppearanceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(AppearanceNotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Eigen exceptie: de actie is niet toegestaan voor deze gebruiker
    // (bv. een appearance aanpassen die van iemand anders is).
    // Antwoord: 403 Forbidden.
    @ExceptionHandler(ForbiddenActionException.class)
    public ResponseEntity<Map<String, String>> handleForbidden(ForbiddenActionException ex) {
        return error(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    // Eigen exceptie: de externe Disney API antwoordt niet of geeft een fout.
    // Antwoord: 502 Bad Gateway = "onze server is in orde,
    // maar de server waar wij van afhangen niet".
    @ExceptionHandler(DisneyApiUnavailableException.class)
    public ResponseEntity<Map<String, String>> handleDisneyDown(DisneyApiUnavailableException ex) {
        return error(HttpStatus.BAD_GATEWAY, ex.getMessage());
    }

    // Hulpmethode die elke handler hierboven gebruikt (DRY: Don't Repeat Yourself).
    // Ze bouwt altijd hetzelfde soort antwoord:
    // status = de meegegeven HTTP-code
    // body = {"error": "<boodschap>"}
    // Zo krijgt de client bij elke fout een JSON-antwoord in hetzelfde formaat.
    private ResponseEntity<Map<String, String>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of("error", message));
    }
}
