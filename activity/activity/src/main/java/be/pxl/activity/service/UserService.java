package be.pxl.activity.service;

import be.pxl.activity.domain.User;
import be.pxl.activity.dto.CreateUserRequest;
import be.pxl.activity.dto.UserResponse;
import be.pxl.activity.exception.EmailAlreadyInUseException;
import be.pxl.activity.exception.InvalidInputException;
import be.pxl.activity.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor-injectie: Spring geeft zelf beide beans mee
    // (de PasswordEncoder staat in WebSecurityConfiguration)
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse register(CreateUserRequest request) {
        // Duidelijke foutmelding, nog voor de unique-constraint van de database
        // toeslaat
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyInUseException(request.getEmail());
        }
        // Het wachtwoord bewaren we nooit leesbaar: enkel de BCrypt-hash
        User user = new User(request.getUsername(), request.getEmail(), passwordEncoder.encode(request.getPassword()));
        return toResponse(userRepository.save(user));
    }

    // Andere services gebruiken dit om de ingelogde gebruiker (via e-mail) op te
    // zoeken
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new InvalidInputException("Unknown user: " + email));
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getUsername(), user.getEmail(), user.getId());
    }
}
