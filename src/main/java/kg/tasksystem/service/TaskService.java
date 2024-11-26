package kg.tasksystem.service;

import kg.tasksystem.dto.TaskDto;
import kg.tasksystem.enums.Priority;
import kg.tasksystem.enums.Role;
import kg.tasksystem.enums.Status;
<<<<<<< Updated upstream
import kg.tasksystem.exception.EntityNotFoundException;
=======
>>>>>>> Stashed changes
import kg.tasksystem.model.Task;
import kg.tasksystem.model.User;
import kg.tasksystem.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
<<<<<<< Updated upstream
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
=======
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
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
=======
    public Task getTask(Long taskId) throws NoSuchElementException {
        return taskRepository.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found"));
    }

    public void create(TaskDto taskDto, Authentication auth) throws NoSuchElementException {
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
        return taskRepository.save(newTask);
    }

    public void edit(Long taskId, TaskDto taskDto) throws EntityNotFoundException {
        Priority priority = Priority.getPriorityEnum(taskDto.getPriority());
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
=======
        taskRepository.save(newTask);
    }

    public void edit(Long taskId, TaskDto taskDto) throws NoSuchElementException {
        Priority priority = Priority.getPriorityEnum(taskDto.getPriority());
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found"));
>>>>>>> Stashed changes
        LocalDateTime updateTime = LocalDateTime.now();
        taskRepository.updateTaskInfo(taskDto.getTitle(), taskDto.getDescription(), priority.toString(), updateTime, task.getId());
    }

<<<<<<< Updated upstream
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
        } else {
            throw new AccessDeniedException("You are not performer of this task");
        }
    }

    public void setPerformer(Long taskId, int userId) throws EntityNotFoundException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
=======
    public void delete(Long taskId) throws NoSuchElementException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found"));
        taskRepository.delete(task);
    }

    public void changeStatus(Long taskId, Authentication auth) throws RuntimeException {
        User user = userService.getByEmail(auth.getName());
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found"));
        if (user.getRole().equals(Role.ROLE_ADMIN.toString()) || Objects.equals(user.getId(), task.getPerformer().getId())) {
            switch (task.getStatus()) {
                case "NEW" -> task.setStatus(Status.IN_PROGRESS.getStatus());
                case "IN_PROGRESS" -> task.setStatus(Status.DONE.getStatus());
                default -> throw new RuntimeException("Task is already DONE");
            }
            taskRepository.updateStatusById(task.getStatus(), task.getId());
        } else {
            throw new RuntimeException("You are not performer of this task");
        }
    }

    public void setPerformer(Long taskId, int userId) throws NoSuchElementException {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new NoSuchElementException("Task not found"));
>>>>>>> Stashed changes
        User performer = userService.getById(userId);
        Status status = Status.IN_PROGRESS;
        taskRepository.updatePerformerAndStatusById(performer, status.toString(), task.getId());
    }
}
