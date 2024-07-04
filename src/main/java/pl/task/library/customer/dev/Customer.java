package pl.task.library.customer.dev;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.context.annotation.Profile;
import pl.task.library.loan.dev.Loan;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Profile("dev")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;
    @PESEL
    private String pesel;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Loan> loans = new ArrayList<>();
}
