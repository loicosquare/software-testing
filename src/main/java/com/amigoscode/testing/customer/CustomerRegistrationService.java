package com.amigoscode.testing.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerRegistrationService {

    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerRegistrationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void RegisterNewCustomer(CustomerRegistrationRequest request) {
        // 1. Phone number is taken
        // 2 . if taken lets check if belongs to same customer
        // - 2.1 if yes return
        // - 2.2 thrown an exception
        // 3. Save customer

        String phoneNumber = request.getCustomer().getPhoneNumber();

        Optional<Customer> optionalCustomer = customerRepository
                .selectCustomerByPhoneNumber(phoneNumber);
        if(optionalCustomer.isPresent()){
            Customer customer = optionalCustomer.get();
            if(customer.getName().equals(request.getCustomer().getName())){
                return;
            }
            throw new IllegalStateException(String.format("phone number [%s] is taken", phoneNumber));
        }
        customerRepository.save(request.getCustomer());
    }
}
