package kg.tasksystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import kg.tasksystem.model.User;
import kg.tasksystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение всех пользователей", description = "Доступно только администратору")
    public @ResponseBody List<User> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        return userService.getUsersOfPage(page, size);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение данных пользователя по ID", description = "Доступно только администратору")
    public @ResponseBody ResponseEntity<User> getUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id));
    }
}
