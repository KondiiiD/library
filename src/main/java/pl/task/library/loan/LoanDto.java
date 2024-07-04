package pl.task.library.loan;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoanDto {
    private String customerFirstName;
    private String customerLastName;
    private String bookTitle;
    private String bookAuthor;
    private String isbn;
    private LocalDateTime dateOfBorrow;
    private LocalDateTime dateOfReturn;
}
