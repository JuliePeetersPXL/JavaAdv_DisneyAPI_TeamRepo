package be.pxl.activity.domain;

import jakarta.persistence.*;

@Entity
// De tabel heet users, want user is een gereserveerd woord in SQL
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // de database geeft het id (auto-increment)
    private Long id;

    @Column(nullable = false)
    private String username;

    // unique = true: in de database kunnen nooit twee gelijke e-mailadressen staan
    @Column(nullable = false, unique = true)
    private String email;

    // Hier staat de BCrypt-hash, niet het wachtwoord zelf
    @Column(nullable = false)
    private String password;

    // JPA maakt objecten via reflectie en heeft een lege constructor nodig
    protected User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Setters enkel voor wat via PUT gewijzigd mag worden
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
