package kg.tasksystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kg.tasksystem.dto.CommentDto;
import kg.tasksystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping
    @Operation(summary = "Добавление комментария к задаче", description = "Доступно только администратору и исполнителю")
    public HttpStatus createComment(@RequestBody @Valid CommentDto comment,
                                    Authentication auth) {
        commentService.create(comment, auth);
        return HttpStatus.OK;
    }
}
