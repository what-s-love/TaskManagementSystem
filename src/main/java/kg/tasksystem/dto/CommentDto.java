package kg.tasksystem.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Добавление комментария к задаче")
public class CommentDto {
    @NotNull
    @NotEmpty
    @Schema(description = "Текст комментария", example = "Новый комментарий")
    private String content;
    @NotNull
    @Schema(description = "ID целевой задачи", example = "1")
    private Long taskId;
}
