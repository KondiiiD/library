package pl.task.library.book;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.task.library.exception.IsbnAlreadyExistsException;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;


    @GetMapping("/{isbn}")
    ResponseEntity<BookDto> findBookByIsbn(@PathVariable String isbn) {
        return bookService.findBookByIsbnNumber(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    ResponseEntity<List<BookDto>> findAllAvailableBooks() {
        return ResponseEntity.ok(bookService.findAllAvailableBooks());
    }

    @PostMapping
    ResponseEntity<BookDto> addBook(@Valid @RequestBody BookDto bookDto) {
        try {
            BookDto savedBook = bookService.saveBook(bookDto);
            URI savedBookUri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("{id}")
                    .buildAndExpand(savedBook.getId())
                    .toUri();
            return ResponseEntity.created(savedBookUri).body(savedBook);
        } catch (IsbnAlreadyExistsException e) {
            return ResponseEntity.badRequest().header("Error", "isbn already exists").build();
        }
    }

    @DeleteMapping("/{isbn}")
    ResponseEntity<?> deleteBook(@PathVariable String isbn) {
        bookService.deleteBook(isbn);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
