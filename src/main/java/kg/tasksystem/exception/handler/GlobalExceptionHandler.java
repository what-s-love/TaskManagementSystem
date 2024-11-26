package kg.tasksystem.exception.handler;

<<<<<<< Updated upstream
import kg.tasksystem.exception.ErrorDetails;
import kg.tasksystem.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
=======
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.tasksystem.exception.ErrorDetails;
import kg.tasksystem.exception.UserNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.atn.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
>>>>>>> Stashed changes

@RestControllerAdvice
public class GlobalExceptionHandler {

<<<<<<< Updated upstream
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> entityNotFoundHandler(EntityNotFoundException exception,
                                                              WebRequest webRequest) {
        return new ResponseEntity<>(buildError(exception, webRequest), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDetails> elementExceptionHandler(NoSuchElementException exception,
                                                              WebRequest webRequest) {
        return new ResponseEntity<>(buildError(exception, webRequest), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> accessDeniedExceptionHandler(AccessDeniedException exception,
                                                              WebRequest webRequest) {
        return new ResponseEntity<>(buildError(exception, webRequest), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorDetails> IOExceptionHandler(IOException exception,
                                                              WebRequest webRequest) {
        return new ResponseEntity<>(buildError(exception, webRequest), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> exceptionHandler(Exception exception,
                                                         WebRequest webRequest) {
        return new ResponseEntity<>(buildError(exception, webRequest), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDetails buildError(Exception ex, WebRequest wr) {
        return new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                wr.getDescription(false)
        );
=======
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException exception,
                                                                    WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception exception,
                                                        WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
                LocalDateTime.now(),
                exception.getMessage(),
                webRequest.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
>>>>>>> Stashed changes
    }
}