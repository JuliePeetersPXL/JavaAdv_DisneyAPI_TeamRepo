package be.pxl.activity.dto;

// Wat we teruggeven. Het wachtwoord staat er bewust niet in.
// Jackson maakt de JSON via de getters (getUserId wordt het veld "userId").
public class UserResponse {

    private final String username;
    private final String email;
    private final Long userId;

    public UserResponse(String username, String email, Long userId) {
        this.username = username;
        this.email = email;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Long getUserId() {
        return userId;
    }
}
