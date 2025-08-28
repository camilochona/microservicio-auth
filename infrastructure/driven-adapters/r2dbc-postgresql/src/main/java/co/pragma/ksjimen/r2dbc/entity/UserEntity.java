package co.pragma.ksjimen.r2dbc.entity;

import org.springframework.data.relational.core.mapping.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.annotation.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Table("users_pragma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column("id_user")
    private Long idUser;
    @Column("name")
    private String name;
    @Column("last_name")
    private String lastName;
    @Column("email")
    private String email;
    @Column("document_type")
    private Integer documentType;
    @Column("document_number")
    private String documentNumber;
    @Column("base_salary")
    private BigDecimal baseSalary;
    @Column("birthdate")
    private LocalDate birthdate;
    @Column("role")
    private String role;
}