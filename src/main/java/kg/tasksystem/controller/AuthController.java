package kg.tasksystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kg.tasksystem.dto.JwtAuthenticationResponse;
import kg.tasksystem.dto.SignInRequest;
import kg.tasksystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Authorization")
    @PostMapping(path = "/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }
}
