package kg.tasksystem.dto;

<<<<<<< Updated upstream
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
=======
>>>>>>> Stashed changes
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
<<<<<<< Updated upstream
@Schema(description = "Создание новой задачи")
public class TaskDto {
    @Size(max = 50, message = "Максимальная длина названия 50 символов")
    @NotBlank(message = "Поле title обязательно для заполнения")
    @Schema(description = "Название задачи", example = "Новая задача")
    private String title;
    @NotBlank(message = "Поле description обязательно для заполнения")
    @Schema(description = "Описание задачи", example = "Создать новую задачу")
    private String description;
    @NotBlank(message = "Поле priority обязательно для заполнения")
    @Schema(description = "Приоритет задачи", example = "HIGH")
=======
public class TaskDto {
    @Size(max = 50)
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
>>>>>>> Stashed changes
    private String priority;
}
