package kg.tasksystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    public static Role getRoleEnum(String role) {
        return switch (role.toUpperCase()) {
            case "ADMIN" -> ADMIN;
            case "USER" -> USER;
            default -> throw new NoSuchElementException(String.format("No such role: '%s'", role));
        };
    }

    @Override
    public String toString() {
        return role;
    }
}
