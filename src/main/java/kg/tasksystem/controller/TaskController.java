package kg.tasksystem.controller;

import kg.tasksystem.model.Task;
import kg.tasksystem.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    //Все задачи + фильтрация (автор и/или исполнитель) + пагинация (ВСЕ)
    //ToDo Не доставать к комменту задачу, к которой он привязан, иначе это рекурсия
    @GetMapping
    public @ResponseBody List<Task> getAll() {
        return taskService.getAll();
    }

    //Задача по id (АДМИН И ИСПОЛНИТЕЛЬ)

    //Создание задачи + валидация + возврат ошибок (АДМИН)

    //Редактирование задачи + валидация + возврат ошибок (АДМИН)

    //Удаление задачи (АДМИН)

    //Изменить статус (АДМИН И ИСПОЛНИТЕЛЬ)
}
