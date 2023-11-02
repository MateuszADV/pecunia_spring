package PecuniaSpring.services.customer;

import PecuniaSpring.models.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    List<Customer> getAllCustomer();
    Customer getCustomerById(Long id);
    Customer saveCustomer(Customer Customer);
    void deleteCustomerById(Long id);

    void updateCustomer(Customer Customer);
}
