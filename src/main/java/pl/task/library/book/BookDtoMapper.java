package pl.task.library.book;

import org.springframework.stereotype.Service;
import pl.task.library.customer.CustomerRepository;


@Service
public class BookDtoMapper {

    private final CustomerRepository customerRepository;

    public BookDtoMapper(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    Book map(BookDto dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setDailyPrice(dto.getDailyPrice());
        return book;
    }

    BookDto map(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setDailyPrice(book.getDailyPrice());
        dto.setIsAvailable(book.getIsAvailable());
        return dto;
    }

}
