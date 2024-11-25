package kg.tasksystem.service;

import kg.tasksystem.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void createComment_without_data_expect_ValidationException() {

    }
}
