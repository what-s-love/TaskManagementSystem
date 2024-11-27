package kg.tasksystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
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
    private final ObjectMapper objectMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение всех пользователей", description = "Доступно только администратору; Пагинация")
    public @ResponseBody ResponseEntity<String> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size) throws JsonProcessingException {
        List<User> users = userService.getUsersOfPage(page, size);
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("UserFilter", SimpleBeanPropertyFilter.serializeAll());
        String jsonResponse = objectMapper.writer(filters).writeValueAsString(users);
        return ResponseEntity.ok(jsonResponse);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Получение данных пользователя по ID", description = "Доступно только администратору")
    public @ResponseBody ResponseEntity<String> getUser(@PathVariable int id) throws JsonProcessingException {
        User user = userService.getById(id);
        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("UserFilter", SimpleBeanPropertyFilter.serializeAll());
        String jsonResponse = objectMapper.writer(filters).writeValueAsString(user);
        return ResponseEntity.ok(jsonResponse);
    }
}
