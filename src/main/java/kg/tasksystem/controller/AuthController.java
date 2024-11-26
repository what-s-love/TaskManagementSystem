package kg.tasksystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kg.tasksystem.dto.JwtAuthenticationResponse;
import kg.tasksystem.dto.SignInRequest;
import kg.tasksystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Авторизация")
    @PostMapping(path = "/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest request, BindingResult result) {
        if (!result.hasErrors()) {
            return ResponseEntity.ok(authenticationService.signIn(request));
        } else {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
    }
}
