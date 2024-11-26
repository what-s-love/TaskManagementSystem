package kg.tasksystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Добавление комментария к задаче")
public class CommentDto {
    @NotBlank(message = "Комментарий не может быть пустым")
    @Schema(description = "Текст комментария", example = "Новый комментарий")
    private String content;
    @NotNull(message = "ID целевой задачи является обязательным полем")
    @Schema(description = "ID целевой задачи", example = "1")
    private Long taskId;
}
