package kg.tasksystem.service;

import kg.tasksystem.dto.CommentDto;
import kg.tasksystem.enums.Role;
import kg.tasksystem.model.Comment;
import kg.tasksystem.model.Task;
import kg.tasksystem.model.User;
import kg.tasksystem.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final TaskService taskService;

    public void create(CommentDto dto, Authentication auth) {
        User author = userService.getByEmail(auth.getName());
        Task task = taskService.getTask(dto.getTaskId(), auth);
        if (author.getRole().equals(Role.ROLE_ADMIN.toString()) || Objects.equals(author.getId(), task.getPerformer().getId())) {
            Comment newComment = Comment.builder()
                    .content(dto.getContent())
                    .task(task)
                    .author(author)
                    .build();
            commentRepository.save(newComment);
        } else {
            throw new AccessDeniedException("You are not performer of this task");
        }
    }

}
