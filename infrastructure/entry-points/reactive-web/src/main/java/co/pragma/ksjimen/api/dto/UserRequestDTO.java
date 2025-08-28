package co.pragma.ksjimen.api.dto;

import co.pragma.ksjimen.model.user.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    private Integer documentType;

    @NotBlank(message = "Document number is required")
    private String documentNumber;

    @NotNull(message = "Base salary is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Base salary must be greater than or equal to 0")
    @DecimalMax(value = "15000000.0", inclusive = true, message = "Base salary must be less than or equal to 15000000")
    private BigDecimal baseSalary;

    @NotNull(message = "Birthdate is required")
    private LocalDate birthdate;

    @NotNull(message = "Role is required")
    private Role role;
}
