package pl.task.library.customer;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

@Repository
@Profile("prod")
public interface CustomerRepository extends MongoRepository<Customer, String> {
    boolean existsByPesel(String pesel);
    boolean existsByEmail(String email);
    Optional<Customer> findByPesel(String pesel);
    Optional<Customer> findByEmail(String email);
}
