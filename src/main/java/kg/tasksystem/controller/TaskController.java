package kg.tasksystem.controller;

<<<<<<< Updated upstream
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kg.tasksystem.dto.TaskDto;
import kg.tasksystem.model.Task;
=======
import jakarta.validation.Valid;
import kg.tasksystem.dto.TaskDto;
import kg.tasksystem.model.Task;
import kg.tasksystem.model.User;
>>>>>>> Stashed changes
import kg.tasksystem.service.Paginator;
import kg.tasksystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
<<<<<<< Updated upstream
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
=======
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

<<<<<<< Updated upstream
    @GetMapping
    @Operation(summary = "Получение всех задач", description = "Доступны фильтры по ID автора/исполнителя задачи, пагинация")
    public @ResponseBody ResponseEntity<List<Task>> getTasks(@RequestParam(name = "authorId", required = false) Integer authorId,
                                           @RequestParam(name = "performerId", required = false) Integer performerId,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "3") int size) {
=======
    //ToDo Не доставать к комменту задачу, к которой он привязан, иначе это рекурсия
    @GetMapping
    public @ResponseBody List<Task> getTasks(@RequestParam(name = "authorId", required = false) Integer authorId,
                                           @RequestParam(name = "performerId", required = false) Integer performerId,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
>>>>>>> Stashed changes
        List<Task> allTasks = taskService.getAll();
        if (authorId != null) {
            allTasks = allTasks.stream().filter(e -> e.getAuthor().getId().equals(authorId)).toList();
        }
        if (performerId != null) {
            allTasks = allTasks.stream().filter(e -> e.getPerformer().getId().equals(authorId)).toList();
        }
        Page<Task> taskPage = new Paginator<Task>().toPage(allTasks, PageRequest.of(page, size));
<<<<<<< Updated upstream
        return ResponseEntity.ok(taskPage.getContent());
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение задачи по ID", description = "Доступно только администратору и исполнителю задачи")
    public @ResponseBody ResponseEntity<Task> getTask(@PathVariable Long id, Authentication auth) {
        return ResponseEntity.ok(taskService.getTask(id, auth));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Создание новой задачи", description = "Доступно только администратору")
    public ResponseEntity<?> create(@RequestBody @Valid TaskDto taskDto,
                                    BindingResult result,
                                    Authentication auth) {
        if (!result.hasErrors()) {
            return ResponseEntity.ok(taskService.create(taskDto, auth));
        } else {
            return ResponseEntity.badRequest().body(result.getAllErrors());
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
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Удаление задачи", description = "Доступно только администратору; Нельзя удалить свою учётку")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    Authentication auth) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/change-status")
    @Operation(summary = "Изменение статуса задачи", description = "Доступно только администратору и исполнителю")
    public ResponseEntity<Task> changeStatus(@PathVariable Long id,
                                             Authentication auth) throws IOException {
        taskService.changeStatus(id, auth);
        return ResponseEntity.ok().build();
=======
        return taskPage.getContent();
    }

    //ToDo Задача по id (АДМИН И ИСПОЛНИТЕЛЬ)
    @GetMapping({"id"})
    public @ResponseBody Task getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    //ToDo Создание задачи + валидация + возврат ошибок (АДМИН)
    @PostMapping("create")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpStatus create(@RequestBody @Valid TaskDto taskDto,
                             Authentication auth) {
        taskService.create(taskDto, auth);
        return HttpStatus.OK;
    }

    //ToDo Редактирование задачи + валидация + возврат ошибок (АДМИН)
    @PostMapping("{id}/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpStatus edit(@PathVariable Long id,
                           @RequestBody @Valid TaskDto taskDto) {
        taskService.edit(id, taskDto);
        return HttpStatus.OK;
    }

    //ToDO Удаление задачи (АДМИН)
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpStatus delete(@PathVariable Long id) {
        taskService.delete(id);
        return HttpStatus.OK;
    }

    //ToDo Изменить статус (АДМИН И ИСПОЛНИТЕЛЬ)
    @GetMapping("{id}/change-status")
    public HttpStatus changeStatus(@PathVariable Long id,
                                   Authentication auth) {
        taskService.changeStatus(id, auth);
        return HttpStatus.OK;
>>>>>>> Stashed changes
    }

    @GetMapping("{id}/set-performer")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
<<<<<<< Updated upstream
    @Operation(summary = "Назначение исполнителя задачи", description = "Доступно только администратору")
    public ResponseEntity<Task> setPerformer(@PathVariable Long id,
                                             @RequestParam int userId) {
        taskService.setPerformer(id, userId);
        return ResponseEntity.ok().build();
=======
    public HttpStatus setPerformer(@PathVariable Long id,
                                   @RequestParam int userId) {
        taskService.setPerformer(id, userId);
        return HttpStatus.OK;
>>>>>>> Stashed changes
    }
}
