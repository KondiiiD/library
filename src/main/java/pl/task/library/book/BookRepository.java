package pl.task.library.book;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
@Repository
@Profile("prod")
public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findBookByIsbn(String isbn);
    List<Book> findAllByIsAvailableIsTrue();
    void deleteByIsbn(String isbn);
    boolean existsByIsbn(String isbn);
}
