package pl.task.library.book;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.task.library.loan.Loan;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
@Profile("prod")
public class Book {
    @Id
    private String id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @ISBN
    private String isbn;
    @Positive
    private Double dailyPrice;
    private Boolean isAvailable;
    private List<Loan> loans = new ArrayList<>();

}
