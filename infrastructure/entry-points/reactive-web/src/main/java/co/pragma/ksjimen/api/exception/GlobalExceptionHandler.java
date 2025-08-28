package co.pragma.ksjimen.api.exception;

import co.pragma.ksjimen.api.dto.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ValidationErrorResponse> handleValidationException(ValidationException ex) {
        return Mono.just(ex.getErrors());
    }

    @ExceptionHandler(ResponseStatusException.class)
    public Mono<ResponseEntity<ValidationErrorResponse>> handleResponseStatusException(ResponseStatusException ex) {
        ValidationErrorResponse response = new ValidationErrorResponse(
                List.of(Map.of("general", List.of(ex.getReason())))
        );
        return Mono.just(ResponseEntity.status(ex.getStatusCode()).body(response));
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Mono<Map<String, String>> handleIllegalStateException(IllegalStateException ex) {
        Map<String, String> error = Map.of("message", ex.getMessage());
        return Mono.just(error);
    }
}
