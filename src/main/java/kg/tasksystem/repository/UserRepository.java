package kg.tasksystem.repository;

import kg.tasksystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmailIgnoreCase(String email);

    User findByEmailIgnoreCase(String email);
}
