package kg.tasksystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kg.tasksystem.dto.TaskDto;
import kg.tasksystem.model.Task;
import kg.tasksystem.service.Paginator;
import kg.tasksystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final ObjectMapper objectMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение всех задач", description = "Доступны фильтры по ID автора/исполнителя задачи, пагинация")
    public @ResponseBody ResponseEntity<String> getTasks(@RequestParam(name = "authorId", required = false) Integer authorId,
                                           @RequestParam(name = "performerId", required = false) Integer performerId,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "3") int size) throws JsonProcessingException {
        List<Task> allTasks = taskService.getAll();
        if (authorId != null) {
            allTasks = allTasks.stream().filter(e -> e.getAuthor().getId().equals(authorId)).toList();
        }
        if (performerId != null) {
            allTasks = allTasks.stream().filter(e -> e.getPerformer().getId().equals(authorId)).toList();
        }
        allTasks = allTasks.stream()
                .sorted(Comparator.comparing(Task::getUpdatedAt).reversed())
                .toList();
        Page<Task> taskPage = new Paginator<Task>().toPage(allTasks, PageRequest.of(page, size));
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("UserFilter", SimpleBeanPropertyFilter.serializeAllExcept("password", "username"));

        String jsonResponse = objectMapper.writer(filters).writeValueAsString(taskPage.getContent());
        return ResponseEntity.ok(jsonResponse);
    }

    @GetMapping(value = "my", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение списка задач, назначенных текущему пользователю на выполнение", description = "Доступно только исполнителю задачи")
    public @ResponseBody ResponseEntity<String> getMyTasks(Authentication auth) throws JsonProcessingException {
        List<Task> tasks = taskService.getMyTasks(auth).stream()
                .sorted(Comparator.comparing(Task::getUpdatedAt).reversed()).toList();
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("UserFilter", SimpleBeanPropertyFilter.filterOutAllExcept("name", "surname", "email"));
        String jsonResponse = objectMapper.writer(filters).writeValueAsString(tasks);
        return ResponseEntity.ok(jsonResponse);
    }


    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение задачи по ID", description = "Доступно только администратору и исполнителю задачи")
    public @ResponseBody ResponseEntity<String> getTask(@PathVariable Long id, Authentication auth) throws JsonProcessingException {
        Task task = taskService.getTask(id, auth);
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("UserFilter", SimpleBeanPropertyFilter.serializeAllExcept("password", "authorities"));
        String jsonResponse = objectMapper.writer(filters).writeValueAsString(task);
        return ResponseEntity.ok(jsonResponse);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Создание новой задачи", description = "Доступно только администратору")
    public ResponseEntity<String> create(@RequestBody @Valid TaskDto taskDto,
                                    BindingResult result,
                                    Authentication auth) throws JsonProcessingException {
        if (!result.hasErrors()) {
            Task task = taskService.create(taskDto, auth);
            FilterProvider filters = new SimpleFilterProvider()
                    .addFilter("UserFilter", SimpleBeanPropertyFilter.serializeAllExcept("password", "authorities"));
            String jsonResponse = objectMapper.writer(filters).writeValueAsString(task);
            return ResponseEntity.ok(jsonResponse);
        } else {
            String jsonResponse = objectMapper.writer().writeValueAsString(result.getAllErrors());
            return ResponseEntity.badRequest().body(jsonResponse);
        }
    }

    @PostMapping("{id}/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Редактирование задачи", description = "Доступно только администратору")
    public ResponseEntity<?> edit(@PathVariable Long id,
                                  @RequestBody @Valid TaskDto taskDto,
                                  BindingResult result) {
        if (!result.hasErrors()) {
            taskService.edit(id, taskDto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Удаление задачи", description = "Доступно только администратору; Нельзя удалить свою учётку")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/change-status")
    @Operation(summary = "Изменение статуса задачи", description = "Доступно только администратору и исполнителю")
    public ResponseEntity<?> changeStatus(@PathVariable Long id,
                                             Authentication auth) throws IOException {
        taskService.changeStatus(id, auth);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/set-performer")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Назначение исполнителя задачи", description = "Доступно только администратору")
    public ResponseEntity<?> setPerformer(@PathVariable Long id,
                                             @RequestParam int userId) {
        taskService.setPerformer(id, userId);
        return ResponseEntity.ok().build();
    }
}
