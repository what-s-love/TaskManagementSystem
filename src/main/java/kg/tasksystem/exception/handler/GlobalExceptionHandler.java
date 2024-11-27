package kg.tasksystem.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
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

@RestControllerAdvice
public class GlobalExceptionHandler {

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
    }
}