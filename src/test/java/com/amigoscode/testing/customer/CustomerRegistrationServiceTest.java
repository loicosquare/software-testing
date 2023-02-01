package com.amigoscode.testing.customer;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CustomerRegistrationServiceTest {

    /* We mock because we don't want to bring all the logic of CustomerRepository
        Because by the CustomerRepositoryTest we know that it's work
        And the integration testing must show that the application is fast
        For that we must not add @DataJpaTest again in this Test Class (the test w'll be slow)
     */
    @Mock
    private CustomerRepository customerRepository; /*= mock(CustomerRepository.class);*/
    private CustomerRegistrationService underTest;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        //Before each test i'm going to have a fresh instance of CustomerRegistration.
        underTest = new CustomerRegistrationService(customerRepository);
    }
}