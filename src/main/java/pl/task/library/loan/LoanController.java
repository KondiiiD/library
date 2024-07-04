package pl.task.library.loan;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.task.library.exception.AlreadyBorrowedException;
import pl.task.library.exception.NoSuchBookException;
import pl.task.library.exception.NoSuchCustomerException;
import pl.task.library.exception.NoSuchLoanException;

import java.util.List;

@RestController
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {
    private final LoanService loanService;


//  Return history of all loans of specific customer
    @GetMapping("/{customerId}")
    ResponseEntity<List<LoanDto>> getAllCustomersLoans(@PathVariable String customerId) {
        return ResponseEntity.ok(loanService.findAllCustomerLoans(customerId));
    }

//  Return history of all loans for specific book by customer
    @GetMapping("/{customerId}/{bookId}")
    ResponseEntity<List<LoanDto>> getBookLoanHistoryForCustomer(@PathVariable String customerId, @PathVariable String bookId) {
        return ResponseEntity.ok(loanService.findAllCustomerLoansByBook(customerId, bookId));
    }


    @PostMapping("/{customerId}/{bookId}")
    ResponseEntity<LoanDto> borrowBook(@PathVariable String customerId, @PathVariable String bookId) {
        try {
            return ResponseEntity.ok(loanService.borrowBook(customerId, bookId));
        } catch (NoSuchBookException | NoSuchCustomerException e) {
            return ResponseEntity.notFound().build();
        } catch (AlreadyBorrowedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PatchMapping("/{customerId}/{bookId}")
    ResponseEntity<LoanCheckoutDto> returnBook(@PathVariable String customerId, @PathVariable String bookId) {
        try {
            return ResponseEntity.ok(loanService.returnBook(customerId, bookId));
        } catch (NoSuchLoanException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
