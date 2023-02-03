package com.fstl.testing.customer;

import com.fstl.testing.utils.PhoneNumberValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CustomerRegistrationService.class})
@ExtendWith(SpringExtension.class)
class CustomerRegistrationServiceTest {

    @Autowired
    private CustomerRegistrationService customerRegistrationService;

    /* We mock because we don't want to bring all the logic of CustomerRepository
          Because by the CustomerRepositoryTest we know that it's work
          And the integration testing must show that the application is fast
          For that we must not add @DataJpaTest again in this Test Class (the test w'll be slow)
       */
    @Mock
    private CustomerRepository customerRepository; /*= mock(CustomerRepository.class);*/

    @Mock
    PhoneNumberValidator phoneNumberValidator; //We mock because w've already tested it.

    private CustomerRegistrationService underTest;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        //Before each test i'm going to have a fresh instance of CustomerRegistration.
        underTest = new CustomerRegistrationService(customerRepository, phoneNumberValidator);
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    void testRegisterNewCustomer() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");

        Customer customer1 = new Customer();
        customer1.setId(UUID.randomUUID());
        customer1.setName("Name");
        customer1.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any())).thenReturn(ofResult);
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        assertThrows(IllegalStateException.class,
                () -> customerRegistrationService.registerNewCustomer(new CustomerRegistrationRequest(new Customer())));
        verify(customerRepository).selectCustomerByPhoneNumber((String) org.mockito.Mockito.any());
        verify(phoneNumberValidator).test((String) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    void testRegisterNewCustomer2() {
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenThrow(new IllegalStateException());
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any()))
                .thenThrow(new IllegalStateException());
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        assertThrows(IllegalStateException.class,
                () -> customerRegistrationService.registerNewCustomer(new CustomerRegistrationRequest(new Customer())));
        verify(customerRepository).selectCustomerByPhoneNumber((String) org.mockito.Mockito.any());
        verify(phoneNumberValidator).test((String) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    void testRegisterNewCustomer3() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any()))
                .thenReturn(Optional.empty());
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        customerRegistrationService.registerNewCustomer(new CustomerRegistrationRequest(new Customer()));
        verify(customerRepository).save((Customer) org.mockito.Mockito.any());
        verify(customerRepository).selectCustomerByPhoneNumber((String) org.mockito.Mockito.any());
        verify(phoneNumberValidator).test((String) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    void testRegisterNewCustomer4() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");

        Customer customer1 = new Customer();
        customer1.setId(UUID.randomUUID());
        customer1.setName("Name");
        customer1.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any())).thenReturn(ofResult);
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(false);
        assertThrows(IllegalStateException.class,
                () -> customerRegistrationService.registerNewCustomer(new CustomerRegistrationRequest(new Customer())));
        verify(phoneNumberValidator).test((String) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegisterNewCustomer5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.customer.Customer.getPhoneNumber()" because the return value of "com.fstl.testing.customer.CustomerRegistrationRequest.getCustomer()" is null
        //       at com.fstl.testing.customer.CustomerRegistrationService.registerNewCustomer(CustomerRegistrationService.java:29)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");

        Customer customer1 = new Customer();
        customer1.setId(UUID.randomUUID());
        customer1.setName("Name");
        customer1.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any())).thenReturn(ofResult);
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        customerRegistrationService.registerNewCustomer(new CustomerRegistrationRequest(null));
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegisterNewCustomer6() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.customer.CustomerRegistrationRequest.getCustomer()" because "request" is null
        //       at com.fstl.testing.customer.CustomerRegistrationService.registerNewCustomer(CustomerRegistrationService.java:29)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");

        Customer customer1 = new Customer();
        customer1.setId(UUID.randomUUID());
        customer1.setName("Name");
        customer1.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any())).thenReturn(ofResult);
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        customerRegistrationService.registerNewCustomer(null);
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    void testRegisterNewCustomer7() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any()))
                .thenReturn(Optional.empty());
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        customerRegistrationService
                .registerNewCustomer(new CustomerRegistrationRequest(new Customer(UUID.randomUUID(), "Name", "4105551212")));
        verify(customerRepository).save((Customer) org.mockito.Mockito.any());
        verify(customerRepository).selectCustomerByPhoneNumber((String) org.mockito.Mockito.any());
        verify(phoneNumberValidator).test((String) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    void testRegisterNewCustomer8() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");

        Customer customer1 = new Customer();
        customer1.setId(UUID.randomUUID());
        customer1.setName("Name");
        customer1.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any())).thenReturn(ofResult);
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        customerRegistrationService
                .registerNewCustomer(new CustomerRegistrationRequest(new Customer(UUID.randomUUID(), "Name", "4105551212")));
        verify(customerRepository).selectCustomerByPhoneNumber((String) org.mockito.Mockito.any());
        verify(phoneNumberValidator).test((String) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegisterNewCustomer9() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.isPresent()" because "optionalCustomer" is null
        //       at com.fstl.testing.customer.CustomerRegistrationService.registerNewCustomer(CustomerRegistrationService.java:38)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any())).thenReturn(null);
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        customerRegistrationService
                .registerNewCustomer(new CustomerRegistrationRequest(new Customer(UUID.randomUUID(), "Name", "4105551212")));
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    void testRegisterNewCustomer10() {
        // Arrange
        // TODO: Populate arranged inputs
        CustomerRegistrationRequest request = null;

        // Act
        this.customerRegistrationService.registerNewCustomer(request);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    void testRegisterNewCustomer11() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");

        Customer customer1 = new Customer();
        customer1.setId(UUID.randomUUID());
        customer1.setName("Name");
        customer1.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any())).thenReturn(ofResult);
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        assertThrows(IllegalStateException.class,
                () -> customerRegistrationService.registerNewCustomer(new CustomerRegistrationRequest(new Customer())));
        verify(customerRepository).selectCustomerByPhoneNumber((String) org.mockito.Mockito.any());
        verify(phoneNumberValidator).test((String) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    void testRegisterNewCustomer12() {
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenThrow(new IllegalStateException());
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any()))
                .thenThrow(new IllegalStateException());
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        assertThrows(IllegalStateException.class,
                () -> customerRegistrationService.registerNewCustomer(new CustomerRegistrationRequest(new Customer())));
        verify(customerRepository).selectCustomerByPhoneNumber((String) org.mockito.Mockito.any());
        verify(phoneNumberValidator).test((String) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    void testRegisterNewCustomer13() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any()))
                .thenReturn(Optional.empty());
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        customerRegistrationService.registerNewCustomer(new CustomerRegistrationRequest(new Customer()));
        verify(customerRepository).save((Customer) org.mockito.Mockito.any());
        verify(customerRepository).selectCustomerByPhoneNumber((String) org.mockito.Mockito.any());
        verify(phoneNumberValidator).test((String) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    void testRegisterNewCustomer14() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");

        Customer customer1 = new Customer();
        customer1.setId(UUID.randomUUID());
        customer1.setName("Name");
        customer1.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any())).thenReturn(ofResult);
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(false);
        assertThrows(IllegalStateException.class,
                () -> customerRegistrationService.registerNewCustomer(new CustomerRegistrationRequest(new Customer())));
        verify(phoneNumberValidator).test((String) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegisterNewCustomer15() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.customer.Customer.getPhoneNumber()" because the return value of "com.fstl.testing.customer.CustomerRegistrationRequest.getCustomer()" is null
        //       at com.fstl.testing.customer.CustomerRegistrationService.registerNewCustomer(CustomerRegistrationService.java:29)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");

        Customer customer1 = new Customer();
        customer1.setId(UUID.randomUUID());
        customer1.setName("Name");
        customer1.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any())).thenReturn(ofResult);
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        customerRegistrationService.registerNewCustomer(new CustomerRegistrationRequest(null));
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegisterNewCustomer16() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.customer.CustomerRegistrationRequest.getCustomer()" because "request" is null
        //       at com.fstl.testing.customer.CustomerRegistrationService.registerNewCustomer(CustomerRegistrationService.java:29)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");

        Customer customer1 = new Customer();
        customer1.setId(UUID.randomUUID());
        customer1.setName("Name");
        customer1.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any())).thenReturn(ofResult);
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        customerRegistrationService.registerNewCustomer(null);
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    void testRegisterNewCustomer17() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any()))
                .thenReturn(Optional.empty());
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        customerRegistrationService
                .registerNewCustomer(new CustomerRegistrationRequest(new Customer(UUID.randomUUID(), "Name", "4105551212")));
        verify(customerRepository).save((Customer) org.mockito.Mockito.any());
        verify(customerRepository).selectCustomerByPhoneNumber((String) org.mockito.Mockito.any());
        verify(phoneNumberValidator).test((String) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    void testRegisterNewCustomer18() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");

        Customer customer1 = new Customer();
        customer1.setId(UUID.randomUUID());
        customer1.setName("Name");
        customer1.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any())).thenReturn(ofResult);
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        customerRegistrationService
                .registerNewCustomer(new CustomerRegistrationRequest(new Customer(UUID.randomUUID(), "Name", "4105551212")));
        verify(customerRepository).selectCustomerByPhoneNumber((String) org.mockito.Mockito.any());
        verify(phoneNumberValidator).test((String) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link CustomerRegistrationService#registerNewCustomer(CustomerRegistrationRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegisterNewCustomer19() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.isPresent()" because "optionalCustomer" is null
        //       at com.fstl.testing.customer.CustomerRegistrationService.registerNewCustomer(CustomerRegistrationService.java:38)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        when(customerRepository.save((Customer) org.mockito.Mockito.any())).thenReturn(customer);
        when(customerRepository.selectCustomerByPhoneNumber((String) org.mockito.Mockito.any())).thenReturn(null);
        when(phoneNumberValidator.test((String) org.mockito.Mockito.any())).thenReturn(true);
        customerRegistrationService
                .registerNewCustomer(new CustomerRegistrationRequest(new Customer(UUID.randomUUID(), "Name", "4105551212")));
    }

    @Test
    void itShouldSaveCustomer() {
        //Given customer and phoneNumber
        String phoneNumber = "00099";
        Customer customer = new Customer(UUID.randomUUID(), "Loic", phoneNumber);

        //... A request taken by the function
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        //... No customer with phoneNumber passed
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).willReturn(Optional.empty());

        //... Valid phone number
        given(phoneNumberValidator.test(phoneNumber)).willReturn(true);

        //When
        underTest.registerNewCustomer(request);

        //Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentValue).isEqualToComparingFieldByField(customer);
    }

    @Test
    void itShouldSaveNewCustomerWhenIdIsNull() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer(null, "Maryam", phoneNumber);

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... No customer with phone number passed
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.empty());

        //... Valid phone number
        given(phoneNumberValidator.test(phoneNumber)).willReturn(true);

        // When
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();
        assertThat(customerArgumentCaptorValue)
                .isEqualToIgnoringGivenFields(customer, "id");
        assertThat(customerArgumentCaptorValue.getId()).isNotNull();
    }

    @Test
    void itShouldNotSaveCustomerWhenCustomerExists() {
        // Given a phone number and a customer
        String phoneNumber = "00099";
        Customer customer = new Customer(UUID.randomUUID(), "Loic", phoneNumber);

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... an existing customer is retuned
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.of(customer));

        //... Valid phone number
        given(phoneNumberValidator.test(phoneNumber)).willReturn(true);

        // When
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should(never()).save(any());
        //then(customerRepository).should().selectCustomerByPhoneNumber(phoneNumber);
        //then(customerRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void itShouldThrowWhenPhoneNumberIsTaken() {
        // Given a phone number and a customer
        String phoneNumber = "000099";
        Customer customer = new Customer(UUID.randomUUID(), "Maryam", phoneNumber);
        Customer customerTwo = new Customer(UUID.randomUUID(), "John", phoneNumber);

        // ... a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // ... No customer with phone number passed
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.of(customerTwo));

        //... Valid phone number
        given(phoneNumberValidator.test(phoneNumber)).willReturn(true);

        // When
        // Then
        assertThatThrownBy(() -> underTest.registerNewCustomer(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("phone number [%s] is taken", phoneNumber));

        // Finally
        then(customerRepository).should(never()).save(any(Customer.class));

    }

    @Test
    void itShouldNotSaveCustomerWhenPhoneNumberIsInvalid() {
        //Given customer and phoneNumber
        String phoneNumber = "00099";
        Customer customer = new Customer(UUID.randomUUID(), "Loic", phoneNumber);

        //... A request taken by the function
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        //... No customer with phoneNumber passed
        //given(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).willReturn(Optional.empty());
        //I comment because w'll no be interaction, this to make the test be faster

        //... Valid phone number
        given(phoneNumberValidator.test(phoneNumber)).willReturn(false);

        //When
        assertThatThrownBy(() -> underTest.registerNewCustomer(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("phone number [%s] is not valid", phoneNumber));

        //Then
        then(customerRepository).shouldHaveNoMoreInteractions();
    }
}