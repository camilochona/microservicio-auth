package co.pragma.ksjimen.api.exception;

import co.pragma.ksjimen.api.dto.ValidationErrorResponse;

import java.util.List;
import java.util.Map;

public class ValidationException extends RuntimeException{
    private final ValidationErrorResponse errors;

    public ValidationException(Map<String, List<String>> fieldErrors) {
        super("Validation failed");
        this.errors = new ValidationErrorResponse(
                List.of(fieldErrors)
        );
    }

    public ValidationErrorResponse getErrors() {
        return errors;
    }
}
