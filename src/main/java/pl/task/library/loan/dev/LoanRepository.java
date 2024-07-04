package pl.task.library.loan.dev;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
@Profile("dev")
interface LoanRepository extends CrudRepository<Loan, Long> {
    List<Loan> findAllByCustomerId(Long customerId);
    List<Loan> findAllByCustomerIdAndBookId(Long customerId, Long bookId);
    Optional<Loan> findLoanByCustomerIdAndBookIdAndDateOfReturnIsNull(Long customerId, Long bookId);
}
