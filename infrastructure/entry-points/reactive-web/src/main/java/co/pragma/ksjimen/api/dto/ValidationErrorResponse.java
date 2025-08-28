package co.pragma.ksjimen.api.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {
    private List<Map<String, List<String>>> errors;
}
