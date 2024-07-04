package pl.task.library.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerDtoMapper {

    CustomerDto map(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setPesel(customer.getPesel());
        return dto;
    }

    Customer map(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        customer.setPesel(dto.getPesel());
        return customer;
    }
}
