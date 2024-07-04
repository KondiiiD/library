package pl.task.library.loan;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.task.library.book.Book;
import pl.task.library.book.BookRepository;
import pl.task.library.customer.Customer;
import pl.task.library.customer.CustomerRepository;
import pl.task.library.exception.AlreadyBorrowedException;
import pl.task.library.exception.NoSuchBookException;
import pl.task.library.exception.NoSuchCustomerException;
import pl.task.library.exception.NoSuchLoanException;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final CustomerRepository customerRepository;
    private final LoanDtoMapper loanDtoMapper;


    @Transactional
    public LoanCheckoutDto returnBook(String customerId, String bookId) {
        Book bookToReturn = findBookById(bookId);

//  Setting book as available in library
        returnBook(bookToReturn);

//  Setting time of return
        Loan loan = findLoanByCustomerIdAndBookId(customerId, bookId);
        loan.setDateOfReturn(LocalDateTime.now());

//  Calculating total price for loan
        double totalPrice = calculateTotalPrice(loan, bookToReturn);
        loan.setTotalPrice(totalPrice);
        loanRepository.save(loan);
//  Mapping entity to response dto
        LoanCheckoutDto loanCheckoutDto = loanDtoMapper.mapCheckout(loan);
        loanCheckoutDto.setTotalPrice(totalPrice);
        return loanCheckoutDto;
    }

    @Transactional
    public LoanDto borrowBook(String customerId, String bookId) {
        Book bookToBorrow = findBookById(bookId);
        Customer borrower = findCustomerById(customerId);

//  Check if book is available in library
        verifyBookAvailability(bookToBorrow);

//  Changing availability of the book
        borrowBook(bookToBorrow);

//  Creating history record
        Loan loan = recordLoan(bookToBorrow, borrower);
        return loanDtoMapper.map(loan);
    }

    public List<LoanDto> findAllCustomerLoans(String customerId) {
        return loanRepository.findAllByCustomerId(customerId)
                .stream()
                .map(loanDtoMapper::map)
                .toList();
    }

    public List<LoanDto> findAllCustomerLoansByBook(String customerId, String bookId) {
        return loanRepository.findAllByCustomerIdAndBookId(customerId, bookId)
                .stream()
                .map(loanDtoMapper::map)
                .toList();
    }

    private void borrowBook(Book bookToBorrow) {
        bookToBorrow.setIsAvailable(false);
        bookRepository.save(bookToBorrow);
    }

    private void returnBook(Book bookToReturn) {
        bookToReturn.setIsAvailable(true);
        bookRepository.save(bookToReturn);
    }

    private Book findBookById(String bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(NoSuchBookException::new);
    }

    private Customer findCustomerById(String customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(NoSuchCustomerException::new);
    }

    private void verifyBookAvailability(Book bookToBorrow) {
        if (!bookToBorrow.getIsAvailable()) {
            throw new AlreadyBorrowedException();
        }
    }

    private Loan recordLoan(Book bookToBorrow, Customer borrower) {
        Loan loan = new Loan();
        loan.setBook(bookToBorrow);
        loan.setCustomer(borrower);
        loan.setDateOfBorrow(LocalDateTime.now());
        loanRepository.save(loan);
        return loan;
    }

    private double calculateTotalPrice(Loan loan, Book bookToReturn) {
        Duration borrowedTime = Duration.between(loan.getDateOfBorrow(), loan.getDateOfReturn());
        long daysOfLoan = borrowedTime.toDays();
        if (borrowedTime.toSeconds()!= 0 || borrowedTime.toHours() != 0) {
            daysOfLoan += 1;
        }
        if (daysOfLoan < 1) {
            daysOfLoan = 1;
        }
        return bookToReturn.getDailyPrice() * daysOfLoan;
    }

    private Loan findLoanByCustomerIdAndBookId(String customerId, String bookId) {
        return loanRepository
                .findLoanByCustomerIdAndBookIdAndDateOfReturnIsNull(customerId, bookId)
                .stream().findAny()
                .orElseThrow(NoSuchLoanException::new);
    }
}
