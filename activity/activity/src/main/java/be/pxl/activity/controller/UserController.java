package be.pxl.activity.controller;

import be.pxl.activity.domain.User;
import be.pxl.activity.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    // ↓ hier, als nieuwe methode
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }
} // ← sluitende accolade van de class, je methode moet hiervoor staan