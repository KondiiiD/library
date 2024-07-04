package pl.task.library.loan;

import org.springframework.stereotype.Service;

@Service
public class LoanDtoMapper {
    LoanDto map(Loan loan) {
        LoanDto dto = new LoanDto();
        dto.setCustomerFirstName(loan.getCustomer().getFirstName());
        dto.setCustomerLastName(loan.getCustomer().getLastName());
        dto.setBookTitle(loan.getBook().getTitle());
        dto.setBookAuthor(loan.getBook().getAuthor());
        dto.setIsbn(loan.getBook().getIsbn());
        dto.setDateOfBorrow(loan.getDateOfBorrow());
        dto.setDateOfReturn(loan.getDateOfReturn());
        return dto;
    }

    LoanCheckoutDto mapCheckout(Loan loan) {
        LoanCheckoutDto dto = new LoanCheckoutDto();
        dto.setCustomerFirstName(loan.getCustomer().getFirstName());
        dto.setCustomerLastName(loan.getCustomer().getLastName());
        dto.setBookTitle(loan.getBook().getTitle());
        dto.setBookAuthor(loan.getBook().getAuthor());
        dto.setIsbn(loan.getBook().getIsbn());
        dto.setDailyPrice(loan.getBook().getDailyPrice());
        dto.setDateOfBorrow(loan.getDateOfBorrow());
        dto.setDateOfReturn(loan.getDateOfReturn());
        return dto;
    }
}
