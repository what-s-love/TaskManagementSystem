package kg.tasksystem.repository;

import kg.tasksystem.model.Task;
import kg.tasksystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Transactional
    @Modifying
    @Query("update Task t set t.updatedAt = ?1 where t.id = ?2")
    int updateUpdatedAtById(LocalDateTime updatedAt, Long id);
    List<Task> findByPerformer_Id(Integer id);
    @Transactional
    @Modifying
    @Query("update Task t set t.performer = ?1, t.status = ?2 where t.id = ?3")
    void updatePerformerAndStatusById(User performer, String status, Long id);
    @Transactional
    @Modifying
    @Query("update Task t set t.status = ?1 where t.id = ?2")
    void updateStatusById(String status, Long id);
    @Transactional
    @Modifying
    @Query("update Task t set t.title = ?1, t.description = ?2, t.priority = ?3, t.updatedAt = ?4 where t.id = ?5")
    void updateTaskInfo(String title, String description, String priority, LocalDateTime updateAt, Long id);
    @Override
    Optional<Task> findById(Long aLong);
}
