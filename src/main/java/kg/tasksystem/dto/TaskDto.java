package kg.tasksystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskDto {
    @Size(max = 50)
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String priority;
}
