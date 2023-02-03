package com.fstl.testing.payment;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import com.fstl.testing.customer.Customer;
import com.fstl.testing.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

class PaymentServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private CardPaymentCharger cardPaymentCharger; /* This in an interface but we Mock it because we have to test in isolation.*/

    private PaymentService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        underTest = new PaymentService(customerRepository, paymentRepository, cardPaymentCharger);
    }

    @Test
    void itShouldChargeCardSuccessFully() {
        //Given
        UUID customerId = UUID.randomUUID();

        //This test is corresponding to the 1 scenario on payment service
        //... Customer Exist
        given(customerRepository.findById(customerId)).willReturn(Optional.of(mock(Customer.class))); //Other to mock we can use new Customer(and pass param as constructor need)

        Currency currency = Currency.USD;

        //... Payment Request
        PaymentRequest paymentRequest = new PaymentRequest(
            new Payment(
                    null,
                    null,
                    new BigDecimal("100.00"),
                    Currency.USD,
                    "card123xx",
                    "Donation"
            )
        );

        //... Card is charged successfully
        given(cardPaymentCharger.chargeCard(
                paymentRequest.getPayment().getSource(),
                String.valueOf(paymentRequest.getPayment().getAmount()),
                paymentRequest.getPayment().getCurrency(),
                paymentRequest.getPayment().getDescription()
        )).willReturn(new CardPaymentCharge(true));

        //When
        underTest.chargeCard(customerId, paymentRequest);

        //Then
        ArgumentCaptor<Payment> paymentArgumentCaptor = ArgumentCaptor.forClass(Payment.class);

        then(paymentRepository).should().save(paymentArgumentCaptor.capture());
        Payment paymentArgumentCaptorValue = paymentArgumentCaptor.getValue();
        assertThat(paymentArgumentCaptorValue)
                .isEqualToIgnoringGivenFields(
                        paymentRequest.getPayment(),
                        "customerId"
                );
        assertThat(paymentArgumentCaptorValue.getCustomerId()).isEqualTo(customerId);
    }

    @Test
    void itShouldThrowWhenCardIsNotCharged() {
        // Given
        UUID customerId = UUID.randomUUID();

        // ... Customer exists
        given(customerRepository.findById(customerId)).willReturn(Optional.of(mock(Customer.class)));

        // ... Payment request
        PaymentRequest paymentRequest = new PaymentRequest(
                new Payment(
                        null,
                        null,
                        new BigDecimal("100.00"),
                        Currency.USD,
                        "card123xx",
                        "Donation"
                )
        );

        // ... Card is not charged successfully
        given(cardPaymentCharger.chargeCard(
                paymentRequest.getPayment().getSource(),
                String.valueOf(paymentRequest.getPayment().getAmount()),
                paymentRequest.getPayment().getCurrency(),
                paymentRequest.getPayment().getDescription()
        )).willReturn(new CardPaymentCharge(false));

        // When
        // Then
        assertThatThrownBy(() -> underTest.chargeCard(customerId, paymentRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Card not debited for customer " + customerId);

        // ... No interaction with paymentRepository
        then(paymentRepository).shouldHaveNoInteractions();
    }

    @Test
    void itShouldNotChargeCardAndThrowWhenCurrencyNotSupported() {
        // Given
        UUID customerId = UUID.randomUUID();

        // ... Customer exists
        given(customerRepository.findById(customerId)).willReturn(Optional.of(mock(Customer.class)));

        // ... Euros
        Currency currency = Currency.EUR;

        // ... Payment request
        PaymentRequest paymentRequest = new PaymentRequest(
                new Payment(
                        null,
                        null,
                        new BigDecimal("100.00"),
                        currency,
                        "card123xx",
                        "Donation"
                )
        );

        // When
        assertThatThrownBy(() -> underTest.chargeCard(customerId, paymentRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Currency [" + currency + "] not supported");

        // Then

        // ... No interaction with cardPaymentCharger
        then(cardPaymentCharger).shouldHaveNoInteractions();

        // ... No interaction with paymentRepository
        then(paymentRepository).shouldHaveNoInteractions();
    }

    @Test
    void itShouldNotChargeAndThrowWhenCustomerNotFound() {
        // Given
        UUID customerId = UUID.randomUUID();

        // Customer not found in db
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());

        // When
        // Then
        assertThatThrownBy(() -> underTest.chargeCard(customerId, new PaymentRequest(new Payment())))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Customer with id [" + customerId + "] not found");

        // ... No interactions with PaymentCharger not PaymentRepository
        then(cardPaymentCharger).shouldHaveNoInteractions();
        then(paymentRepository).shouldHaveNoInteractions();
    }
}