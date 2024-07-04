package pl.task.library.customer.dev;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@Profile("dev")
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    boolean existsByPesel(String pesel);
    boolean existsByEmail(String email);
    Optional<Customer> findByPesel(String pesel);
    Optional<Customer> findByEmail(String email);
}
