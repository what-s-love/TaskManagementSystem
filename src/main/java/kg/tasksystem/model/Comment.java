package kg.tasksystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "COMMENTS")
public class Comment {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CONTENT", columnDefinition = "CHARACTER LARGE OBJECT(0, 0) not null")
    @Lob
    private String content;
    @OneToOne
    @JoinColumn(name = "AUTHORID", nullable = false)
    private User author;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TASKID", nullable = false)
    private Task task;
}