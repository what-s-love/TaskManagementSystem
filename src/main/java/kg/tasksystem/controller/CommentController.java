package kg.tasksystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kg.tasksystem.dto.CommentDto;
import kg.tasksystem.model.Comment;
import kg.tasksystem.model.Task;
import kg.tasksystem.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ObjectMapper objectMapper;

    @Operation(summary = "Добавление комментария к задаче", description = "Доступно только администратору и исполнителю")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createComment(@RequestBody @Valid CommentDto comment,
                                           BindingResult result,
                                           Authentication auth) throws JsonProcessingException {
        if (!result.hasErrors()) {
            Comment newComment = commentService.create(comment, auth);
            FilterProvider filters = new SimpleFilterProvider()
                    .addFilter("UserFilter", SimpleBeanPropertyFilter.filterOutAllExcept("name", "surname", "email"));
            String jsonResponse = objectMapper.writer(filters).writeValueAsString(newComment);
            return ResponseEntity.ok(jsonResponse);
        } else {
            String jsonResponse = objectMapper.writer().writeValueAsString(result.getAllErrors());
            return ResponseEntity.badRequest().body(jsonResponse);
        }
    }
}
