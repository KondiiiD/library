package pl.task.library.loan;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pl.task.library.book.Book;
import pl.task.library.customer.Customer;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
@Profile("prod")
public class Loan {
    @Id
    private String id;
    @DBRef
    private Customer customer;
    @DBRef
    private Book book;
    private LocalDateTime dateOfBorrow;
    private LocalDateTime dateOfReturn;
    private Double totalPrice;
}
