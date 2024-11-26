package kg.tasksystem.service;

import kg.tasksystem.dto.CommentDto;
import kg.tasksystem.exception.EntityNotFoundException;
import kg.tasksystem.model.Comment;
import kg.tasksystem.model.Task;
import kg.tasksystem.model.User;
import kg.tasksystem.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private TaskService taskService;
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentService commentService;

    @Test
    void createComment_ShouldSaveComment_WhenUserIsAdmin() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("admin@example.com");

        User admin = User.builder().id(1).role("ROLE_ADMIN").build();
        Task task = Task.builder().id(1L).build();
        CommentDto dto = CommentDto.builder().taskId(1L).content("Тестовый комментарий").build();

        when(userService.getByEmail("admin@example.com")).thenReturn(admin);
        when(taskService.getTask(1L, auth)).thenReturn(task);

        commentService.create(dto, auth);

        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(commentCaptor.capture());

        Comment savedComment = commentCaptor.getValue();
        assertEquals("Тестовый комментарий", savedComment.getContent());
        assertEquals(task, savedComment.getTask());
        assertEquals(admin, savedComment.getAuthor());
    }

    @Test
    void createComment_ShouldSaveComment_WhenUserIsPerformer() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@example.com");

        User performer = User.builder().id(2).role("ROLE_USER").build();
        Task task = Task.builder().id(1L).performer(performer).build();
        CommentDto dto = CommentDto.builder().taskId(1L).content("Комментарий от исполнителя").build();

        when(userService.getByEmail("user@example.com")).thenReturn(performer);
        when(taskService.getTask(1L, auth)).thenReturn(task);

        commentService.create(dto, auth);

        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(commentCaptor.capture());

        Comment savedComment = commentCaptor.getValue();
        assertEquals("Комментарий от исполнителя", savedComment.getContent());
        assertEquals(task, savedComment.getTask());
        assertEquals(performer, savedComment.getAuthor());
    }

    @Test
    void createComment_ShouldThrowAccessDeniedException_WhenUserIsNotAdminOrPerformer() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@example.com");

        User user = User.builder().id(3).role("ROLE_USER").build();
        User performer = User.builder().id(2).build();
        Task task = Task.builder().id(1L).performer(performer).build();
        CommentDto dto = CommentDto.builder().taskId(1L).content("Комментарий от неавторизованного пользователя").build();

        when(userService.getByEmail("user@example.com")).thenReturn(user);
        when(taskService.getTask(1L, auth)).thenReturn(task);

        AccessDeniedException exception = assertThrows(
                AccessDeniedException.class,
                () -> commentService.create(dto, auth)
        );

        assertEquals("You are not performer of this task", exception.getMessage());
        verifyNoInteractions(commentRepository);
    }

    @Test
    void createComment_ShouldThrowException_WhenTaskNotFound() {
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("admin@example.com");

        User admin = User.builder().id(1).role("ROLE_ADMIN").build();
        CommentDto dto = CommentDto.builder().taskId(999L).content("Комментарий для несуществующей задачи").build();

        when(userService.getByEmail("admin@example.com")).thenReturn(admin);
        when(taskService.getTask(999L, auth)).thenThrow(new EntityNotFoundException("Task not found"));

        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> commentService.create(dto, auth)
        );

        assertEquals("Task not found", exception.getMessage());
        verifyNoInteractions(commentRepository);
    }
}
