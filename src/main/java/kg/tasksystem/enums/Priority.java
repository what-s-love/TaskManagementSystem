package kg.tasksystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;
@Getter
@RequiredArgsConstructor
public enum Priority {
    HIGH("HIGH"),
    MEDIUM("MEDIUM"),
    LOW("LOW");

    private final String priority;

    public static Priority getPriorityEnum(String priority) {
        return switch (priority.toUpperCase()) {
            case "HIGH" -> HIGH;
            case "MEDIUM" -> MEDIUM;
            case "LOW" -> LOW;
            default -> throw new NoSuchElementException(String.format("No such priority: '%s'", priority));
        };
    }

    @Override
    public String toString() {
        return priority;
    }
}
