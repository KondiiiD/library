package pl.task.library.customer;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.task.library.exception.EmailAlreadyExistsException;
import pl.task.library.exception.PeselAlreadyExistsException;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    @GetMapping("/{id}")
    ResponseEntity<CustomerDto> findCustomerById(@PathVariable String id) {
        return customerService.findCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<CustomerDto> registerCustomer(@Valid @RequestBody CustomerDto customerDto) {
        try {
            CustomerDto registeredCustomer = customerService.saveCustomer(customerDto);
            URI registeredCustomerUri = ServletUriComponentsBuilder.
                    fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(registeredCustomer.getId())
                    .toUri();
            return ResponseEntity.created(registeredCustomerUri).body(registeredCustomer);
        } catch (PeselAlreadyExistsException e) {
            return ResponseEntity.badRequest().header("Error", "Pesel already exists").build();
        } catch (EmailAlreadyExistsException e) {
            return ResponseEntity.badRequest().header("Error", "Email already exists").build();
        }
    }

    @PutMapping("/{customerId}")
    ResponseEntity<?> updateCustomer(@PathVariable String customerId, @Valid @RequestBody CustomerDto customerDto) {
        return customerService.replaceCustomer(customerId, customerDto)
                .map(c -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{customerId}")
    ResponseEntity<?> deleteCustomer(@PathVariable String customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
