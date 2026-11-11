package be.pxl.activity.controller;

import be.pxl.activity.dto.CreateUserRequest;
import be.pxl.activity.dto.UserResponse;
import be.pxl.activity.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController // elke methode geeft JSON terug, geen HTML-pagina
@RequestMapping("/api/users") // gemeenschappelijk begin van de URL voor deze klasse
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // succes = 201 in plaats van 200
    public UserResponse register(@Valid @RequestBody CreateUserRequest request) {
        // @RequestBody: JSON uit de body wordt een object. @Valid: controleer de
        // beperkingen
        return userService.register(request);
    }
}
