package pl.task.library.customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.task.library.exception.EmailAlreadyExistsException;
import pl.task.library.exception.PeselAlreadyExistsException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerDtoMapper dtoMapper;


    public Optional<CustomerDto> findCustomerById(String id) {
        return customerRepository.findById(id)
                .map(dtoMapper::map);
    }

    @Transactional
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        Customer customer = dtoMapper.map(customerDto);
        validateUniquenessInputData(customer);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.map(savedCustomer);
    }

    @Transactional
    public Optional<CustomerDto> replaceCustomer(String customerId, CustomerDto customerDto) {
        if (!customerRepository.existsById(customerId)) {
            return Optional.empty();
        }
        customerDto.setId(customerId);
        Customer customerUpdateData = dtoMapper.map(customerDto);

        Customer updatedCustomer = customerRepository.save(customerUpdateData);
        return Optional.of(dtoMapper.map(updatedCustomer));
    }

    @Transactional
    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }

    private void validateUniquenessInputData(Customer customer) {
        if (customerRepository.existsByPesel(customer.getPesel())) {
            throw new PeselAlreadyExistsException();
        }
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new EmailAlreadyExistsException();
        }
    }
}
