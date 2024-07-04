package pl.task.library.book.dev;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.ISBN;
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
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @ISBN
    private String isbn;
    @Positive
    private Double dailyPrice;
    private Boolean isAvailable;
    @OneToMany(mappedBy = "book", cascade = CascadeType.REMOVE)
    private List<Loan> loans = new ArrayList<>();

}
