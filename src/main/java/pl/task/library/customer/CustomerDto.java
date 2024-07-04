package pl.task.library.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.pl.PESEL;

@Setter
@Getter
@NoArgsConstructor
public class CustomerDto {
    private String id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;
    @PESEL
    private String pesel;
}
