package ma.tr.docnearme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private record ErrorResponse(
            String message,
            HttpStatus status,
            LocalDateTime timestamp,
            Map<String, String> errors
    ) {}

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(message, status, LocalDateTime.now(), null));
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status, Map<String, String> errors) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(message, status, LocalDateTime.now(), errors));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return buildErrorResponse("Validation failed", HttpStatus.UNPROCESSABLE_ENTITY, errors);
    }

    @ExceptionHandler(PermissionException.class)
    public ResponseEntity<ErrorResponse> handlePermissionException(PermissionException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotAuthException.class)
    public ResponseEntity<ErrorResponse> handleNotAuthException(NotAuthException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }



    @ExceptionHandler(ProcessNotCompletedException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(ProcessNotCompletedException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}