package be.pxl.activity.repository;

import be.pxl.activity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Spring maakt de implementatie zelf. De methodes worden op naam omgezet naar SQL:
// existsByEmail -> select count(*) ... where email = ?
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
