package be.pxl.activity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Wat de client meestuurt bij registratie. De beperkingen (@NotBlank, @Email) controleert @Valid in de controller.
// Een gewone klasse: Jackson maakt het object met de lege constructor en vult het via de setters.
public class CreateUserRequest {

    @NotBlank
    private String username;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

    public CreateUserRequest() {
        // nodig voor Jackson (JSON -> object)
    }

    public CreateUserRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
