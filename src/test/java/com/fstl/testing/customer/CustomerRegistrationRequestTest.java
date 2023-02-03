package com.fstl.testing.customer;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class CustomerRegistrationRequestTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link CustomerRegistrationRequest#CustomerRegistrationRequest(Customer)}
     *   <li>{@link CustomerRegistrationRequest#toString()}
     *   <li>{@link CustomerRegistrationRequest#getCustomer()}
     * </ul>
     */
    @Test
    void testConstructor() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        CustomerRegistrationRequest actualCustomerRegistrationRequest = new CustomerRegistrationRequest(customer);
        actualCustomerRegistrationRequest.toString();
        assertSame(customer, actualCustomerRegistrationRequest.getCustomer());
    }
}

