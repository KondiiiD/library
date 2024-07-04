package pl.task.library.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.UniqueElements;

@Getter
@Setter
@NoArgsConstructor
public class BookDto {
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
}
