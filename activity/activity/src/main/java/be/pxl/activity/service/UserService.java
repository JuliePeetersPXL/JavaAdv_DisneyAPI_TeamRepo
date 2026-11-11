package be.pxl.activity.service;

import be.pxl.activity.domain.User;
import be.pxl.activity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User update(Long id, User nieuweGegevens) {
        User bestaande = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User niet gevonden: " + id));

        bestaande.setName(nieuweGegevens.getName()); // jouw velden
        bestaande.setEmail(nieuweGegevens.getEmail());

        return userRepository.save(bestaande);
    }
}