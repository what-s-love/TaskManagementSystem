package kg.tasksystem.service;

import kg.tasksystem.dto.JwtAuthenticationResponse;
import kg.tasksystem.dto.SignInRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userService.loadUserByUsername(request.getEmail());

        var jwt = jwtService.generateToken(user);
        log.info("SingInGeneratedToken: " + jwt);
        return new JwtAuthenticationResponse(jwt);
    }
}
