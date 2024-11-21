package kg.tasksystem.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.tasksystem.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class JwtCsrfFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final String csrfHeaderName = "x-csrf-token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getHeader("Authorization") != null && request.getHeader("Authorization").startsWith("Basic ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtService.extractToken(request, csrfHeaderName);

        if (request.getServletPath().equals("/auth/login")) {
            String newToken = jwtService.generateToken();
            response.setHeader(csrfHeaderName, newToken);
            filterChain.doFilter(request, response);
        } else {
            if (token != null && jwtService.validateToken(token)) {
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid or missing CSRF token");
            }
        }
    }
}

