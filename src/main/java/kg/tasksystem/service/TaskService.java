package kg.tasksystem.service;

import kg.tasksystem.dto.TaskDto;
import kg.tasksystem.enums.Priority;
import kg.tasksystem.enums.Role;
import kg.tasksystem.enums.Status;
import kg.tasksystem.exception.EntityNotFoundException;
import kg.tasksystem.model.Task;
import kg.tasksystem.model.User;
import kg.tasksystem.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Task getTask(Long taskId, Authentication auth) throws EntityNotFoundException, AccessDeniedException {
        User user = userService.getByEmail(auth.getName());
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        if (user.getRole().equals(Role.ROLE_ADMIN.toString()) || Objects.equals(user.getId(), task.getPerformer().getId())) {
            return task;
        } else {
            throw new AccessDeniedException("You are not performer of this task");
        }
    }

    public Task create(TaskDto taskDto, Authentication auth) throws EntityNotFoundException {
        Priority priority = Priority.getPriorityEnum(taskDto.getPriority());
        User author = userService.getByEmail(auth.getName());

        Task newTask = Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .status(Status.NEW.getStatus())
                .priority(priority.toString())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .author(author)
                .build();

        return taskRepository.save(newTask);
    }

    public void edit(Long taskId, TaskDto taskDto) throws EntityNotFoundException {
        Priority priority = Priority.getPriorityEnum(taskDto.getPriority());
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        LocalDateTime updateTime = LocalDateTime.now();
        taskRepository.updateTaskInfo(taskDto.getTitle(), taskDto.getDescription(), priority.toString(), updateTime, task.getId());
    }

    public void delete(Long taskId) throws EntityNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

    public void changeStatus(Long taskId, Authentication auth) throws IOException, RuntimeException {
        User user = userService.getByEmail(auth.getName());
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        if (user.getRole().equals(Role.ROLE_ADMIN.toString()) || Objects.equals(user.getId(), task.getPerformer().getId())) {
            switch (task.getStatus().toUpperCase()) {
                case "NEW" -> task.setStatus(Status.IN_PROGRESS.toString());
                case "IN_PROGRESS" -> task.setStatus(Status.DONE.toString());
                default -> throw new IOException("Task is already DONE");
            }
            taskRepository.updateStatusById(task.getStatus(), task.getId());
            taskRepository.updateUpdatedAtById(LocalDateTime.now(), taskId);
        } else {
            throw new AccessDeniedException("You are not performer of this task");
        }
    }

    public void setPerformer(Long taskId, int userId) throws EntityNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        User performer = userService.getById(userId);
        Status status = Status.IN_PROGRESS;
        taskRepository.updatePerformerAndStatusById(performer, status.toString(), task.getId());
        taskRepository.updateUpdatedAtById(LocalDateTime.now(), taskId);
    }

    public List<Task> getMyTasks(Authentication auth) {
        User user = userService.getByEmail(auth.getName());
        return taskRepository.findByPerformer_Id(user.getId());
    }
}
