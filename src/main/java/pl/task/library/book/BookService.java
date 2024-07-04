package pl.task.library.book;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.task.library.exception.IsbnAlreadyExistsException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookDtoMapper bookDtoMapper;


    public Optional<BookDto> findBookByIsbnNumber(String isbn) {
        return bookRepository.findBookByIsbn(isbn)
                .map(bookDtoMapper::map);
    }

    public List<BookDto> findAllAvailableBooks() {
        return bookRepository.findAllByIsAvailableIsTrue()
                .stream()
                .map(bookDtoMapper::map)
                .toList();
    }

    @Transactional
    public BookDto saveBook(BookDto bookDto) {
        Book book = bookDtoMapper.map(bookDto);
        book.setIsAvailable(true);
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new IsbnAlreadyExistsException();
        }
        Book savedBook = bookRepository.save(book);
        return bookDtoMapper.map(savedBook);
    }

    public void deleteBook(String isbn) {
        bookRepository.deleteByIsbn(isbn);
    }

}
