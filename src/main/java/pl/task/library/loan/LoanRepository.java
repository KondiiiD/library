package pl.task.library.loan;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("prod")
public interface LoanRepository extends MongoRepository<Loan, Long> {
    List<Loan> findAllByCustomerId(String customerId);
    List<Loan> findAllByCustomerIdAndBookId(String customerId, String bookId);
    Optional<Loan> findLoanByCustomerIdAndBookIdAndDateOfReturnIsNull(String customerId, String bookId);
}
