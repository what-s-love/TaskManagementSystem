package kg.tasksystem.service;

<<<<<<< Updated upstream
import kg.tasksystem.exception.EntityNotFoundException;
=======
import kg.tasksystem.exception.UserNotFoundException;
>>>>>>> Stashed changes
import kg.tasksystem.model.User;
import kg.tasksystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public List<User> getUsersOfPage(int pageNumber, int pageSize) {
        List<User> allUsers = userRepository.findAll();
        Page<User> userPage = new Paginator<User>().toPage(allUsers, PageRequest.of(pageNumber, pageSize));
        return userPage.getContent();
    }

    public User getByEmail(String email) throws EntityNotFoundException {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> {
                    log.warn("User with email {} not found", email);
<<<<<<< Updated upstream
                    return new EntityNotFoundException(String.format("User %s is not found", email));
                });
    }

    public User getById(int id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", id);
                    return new EntityNotFoundException(String.format("User with ID %d is not found", id));
=======
                    return new UserNotFoundException(String.format("User %s is not found", email));
                });
    }

    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User with id {} not found", id);
                    return new UsernameNotFoundException(String.format("User with ID %d is not found", id));
>>>>>>> Stashed changes
                });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getByEmail(username);
    }
}
