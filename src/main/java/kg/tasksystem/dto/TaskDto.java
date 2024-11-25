package kg.tasksystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Создание новой задачи")
public class TaskDto {
    @Size(max = 50, message = "Максимальная длина названия 50 символов")
    @NotNull
    @NotEmpty
    @Schema(description = "Название задачи", example = "Новая задача")
    private String title;
    @NotNull
    @NotEmpty
    @Schema(description = "Описание задачи", example = "Создать новую задачу")
    private String description;
    @NotNull
    @NotEmpty
    @Schema(description = "Приоритет задачи", example = "HIGH")
    private String priority;
}
