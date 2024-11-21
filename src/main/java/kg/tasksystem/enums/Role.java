package kg.tasksystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String role;

    public static Role getRoleEnum(String role) {
        return switch (role.toUpperCase()) {
            case "ROLE_ADMIN" -> ROLE_ADMIN;
            case "ROLE_USER" -> ROLE_USER;
            default -> throw new NoSuchElementException(String.format("No such role: '%s'", role));
        };
    }

    @Override
    public String toString() {
        return role;
    }
}
