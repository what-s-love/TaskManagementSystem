package kg.tasksystem.service;

import kg.tasksystem.dto.TaskDto;
import kg.tasksystem.enums.Priority;
import kg.tasksystem.enums.Status;
import kg.tasksystem.exception.EntityNotFoundException;
import kg.tasksystem.model.Task;
import kg.tasksystem.model.User;
import kg.tasksystem.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_ShouldReturnSavedTask() throws EntityNotFoundException {
        String email = "test@example.com";
        String title = "Test task";
        String description = "This is a test task.";
        String priority = Priority.LOW.toString();
        TaskDto taskDto = TaskDto.builder()
                .title(title)
                .description(description)
                .priority(priority)
                .build();

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(email);

        User mockUser = User.builder().id(1).email(email).build();

        when(userService.getByEmail(email)).thenReturn(mockUser);

        Task savedTask = Task.builder()
                .id(1L)
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .status(Status.NEW.toString())
                .priority(Priority.HIGH.toString())
                .author(mockUser)
                .build();

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.create(taskDto, auth);

        assertNotNull(result);
        assertEquals("Test task", result.getTitle());
        assertEquals("This is a test task.", result.getDescription());
        assertEquals(Status.NEW.getStatus(), result.getStatus());
        assertEquals(Priority.HIGH.toString(), result.getPriority());
        assertEquals(mockUser, result.getAuthor());

        verify(userService).getByEmail(email);
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void createTask_ShouldThrowException_UserNotFound() {
        String email = "wrongUser@example.com";
        String title = "Test task";
        String description = "This is a test task.";
        String priority = Priority.HIGH.toString();
        TaskDto taskDto = TaskDto.builder()
                .title(title)
                .description(description)
                .priority(priority)
                .build();

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn(email);

        when(userService.getByEmail(email)).thenThrow(new EntityNotFoundException("User not found"));

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> taskService.create(taskDto, auth)
        );

        assertEquals("User not found", exception.getMessage());
        verify(userService).getByEmail(email);
        verifyNoInteractions(taskRepository);
    }

    @Test
    void deleteTask_ShouldDeleteSuccessfully_WhenTaskExists() {
        Long taskId = 1L;
        Task mockTask = Task.builder().id(taskId).build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(mockTask));

        taskService.delete(taskId);

        verify(taskRepository).findById(taskId);
        verify(taskRepository).delete(mockTask);
    }

    @Test
    void deleteTask_ShouldThrowException_WhenTaskDoesNotExist() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> taskService.delete(taskId)
        );

        assertEquals("Task not found", exception.getMessage());
        verify(taskRepository).findById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void changeStatus_ShouldUpdateStatus_WhenUserIsAdmin() throws Exception {
        Long taskId = 1L;
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("admin@example.com");

        int userId = 1;
        String role = "ROLE_ADMIN";
        User admin = User.builder().id(userId).role(role).build();

        Task task = Task.builder().id(taskId).status("NEW").build();

        when(userService.getByEmail("admin@example.com")).thenReturn(admin);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.changeStatus(taskId, auth);

        assertEquals(Status.IN_PROGRESS.getStatus(), task.getStatus());
        verify(taskRepository).updateStatusById(Status.IN_PROGRESS.getStatus(), taskId);
    }

    @Test
    void changeStatus_ShouldUpdateStatus_WhenUserIsPerformer() throws Exception {
        Long taskId = 1L;
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@example.com");

        User performer = User.builder().id(2).role("ROLE_USER").build();
        Task task = Task.builder().id(taskId).status("NEW").performer(performer).build();

        when(userService.getByEmail("user@example.com")).thenReturn(performer);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.changeStatus(taskId, auth);

        assertEquals(Status.IN_PROGRESS.getStatus(), task.getStatus());
        verify(taskRepository).updateStatusById(Status.IN_PROGRESS.getStatus(), taskId);
    }

    @Test
    void changeStatus_ShouldThrowEntityNotFoundException_WhenTaskDoesNotExist() {
        Long taskId = 1L;
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@example.com");

        User user = User.builder().id(1).build();

        when(userService.getByEmail("user@example.com")).thenReturn(user);
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> taskService.changeStatus(taskId, auth)
        );

        assertEquals("Task not found", exception.getMessage());
        verify(taskRepository).findById(taskId);
    }

    @Test
    void changeStatus_ShouldThrowAccessDeniedException_WhenUserIsNotAdminOrPerformer() {
        Long taskId = 1L;
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@example.com");

        User user = User.builder().id(1).role("ROLE_USER").build();
        User performer = User.builder().id(2).build();
        Task task = Task.builder().id(taskId).status("NEW").performer(performer).build();

        when(userService.getByEmail("user@example.com")).thenReturn(user);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        AccessDeniedException exception = assertThrows(
                AccessDeniedException.class,
                () -> taskService.changeStatus(taskId, auth)
        );

        assertEquals("You are not performer of this task", exception.getMessage());
    }

    @Test
    void changeStatus_ShouldThrowIOException_WhenTaskIsAlreadyDone() {
        Long taskId = 1L;
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("admin@example.com");

        User admin = User.builder().id(1).role("ROLE_ADMIN").build();
        Task task = Task.builder().id(taskId).status("DONE").build();

        when(userService.getByEmail("admin@example.com")).thenReturn(admin);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        IOException exception = assertThrows(
                IOException.class,
                () -> taskService.changeStatus(taskId, auth)
        );

        assertEquals("Task is already DONE", exception.getMessage());
    }
}