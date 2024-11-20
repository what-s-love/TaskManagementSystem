package kg.tasksystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TASKS")
public class Task {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "DESCRIPTION", columnDefinition = "CHARACTER LARGE OBJECT(0, 0) not null")
    @Lob
    private String description;
    private String status;
    private String priority;
    @NotNull
    @Column(name = "TIME", nullable = false)
    private LocalDateTime createdAt;
    @NotNull
    @Column(name = "TIME", nullable = false)
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
    @OneToOne
    @JoinColumn(name = "AUTHORID", nullable = false)
    private User author;
    @OneToOne
    @JoinColumn(name = "PERFORMERID")
    private User performer;
}
