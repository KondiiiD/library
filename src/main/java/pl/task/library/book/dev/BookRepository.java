package pl.task.library.book.dev;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@Profile("dev")
public interface BookRepository extends CrudRepository<Book, Long> {
    Optional<Book> findBookByIsbn(String isbn);
    List<Book> findAllByIsAvailableIsTrue();
    void deleteByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
}
