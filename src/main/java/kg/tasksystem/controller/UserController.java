package kg.tasksystem.controller;

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
    public @ResponseBody List<User> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {
        return userService.getUsersOfPage(page, size);
    }

/*
    @GetMapping({"id"})
    public @ResponseBody ResponseEntity<User> getUser(@PathVariable int id) {
        return ResponseEntity.ok(userService.getById(id));
    }
*/
    @GetMapping({"id"})
    public @ResponseBody User getUser(@PathVariable int id) {
        return userService.getById(id);
    }
}
