package pl.task.library.loan.dev;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import pl.task.library.book.dev.Book;
import pl.task.library.customer.dev.Customer;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Profile("dev")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "borrowerId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    private LocalDateTime dateOfBorrow;
    private LocalDateTime dateOfReturn;
    private Double totalPrice;
}
