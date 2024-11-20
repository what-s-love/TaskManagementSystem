package kg.tasksystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TASKS")
public class Task {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 50)
    @NotNull
    @Column(name = "TITLE", nullable = false, length = 50)
    private String title;
    @Column(name = "DESCRIPTION", nullable = false)
    @Lob
    private String description;
    @NotNull
    @Column(name = "STATUS", nullable = false)
    private String status;
    @NotNull
    @Column(name = "PRIORITY", nullable = false)
    private String priority;
    @NotNull
    @Column(name = "CREATETIME", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "UPDATETIME", nullable = false)
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