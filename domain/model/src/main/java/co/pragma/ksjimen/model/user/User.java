package co.pragma.ksjimen.model.user;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {
    private Long idUser;
    private String name;
    private String lastName;
    private String email;
    private Integer documentType;
    private String documentNumber;
    private BigDecimal baseSalary;
    private LocalDate birthdate;
    private Role role;
}
