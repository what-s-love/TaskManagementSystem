package kg.tasksystem.controller;

import jakarta.validation.Valid;
import kg.tasksystem.dto.TaskDto;
import kg.tasksystem.model.Task;
import kg.tasksystem.model.User;
import kg.tasksystem.service.Paginator;
import kg.tasksystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    //ToDo Не доставать к комменту задачу, к которой он привязан, иначе это рекурсия
    @GetMapping
    public @ResponseBody List<Task> getTasks(@RequestParam(name = "authorId", required = false) Integer authorId,
                                           @RequestParam(name = "performerId", required = false) Integer performerId,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        List<Task> allTasks = taskService.getAll();
        if (authorId != null) {
            allTasks = allTasks.stream().filter(e -> e.getAuthor().getId().equals(authorId)).toList();
        }
        if (performerId != null) {
            allTasks = allTasks.stream().filter(e -> e.getPerformer().getId().equals(authorId)).toList();
        }
        Page<Task> taskPage = new Paginator<Task>().toPage(allTasks, PageRequest.of(page, size));
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
    }

    @GetMapping("{id}/set-performer")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public HttpStatus setPerformer(@PathVariable Long id,
                                   @RequestParam int userId) {
        taskService.setPerformer(id, userId);
        return HttpStatus.OK;
    }
}
