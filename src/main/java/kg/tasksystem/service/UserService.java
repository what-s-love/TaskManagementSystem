package kg.tasksystem.service;

import kg.tasksystem.model.User;
import kg.tasksystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            return userRepository.findByEmailIgnoreCase(email);
        } else {
            return null;
        }
    }
}
