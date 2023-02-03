package com.fstl.testing.payment;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fstl.testing.customer.Customer;
import com.fstl.testing.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {PaymentService.class})
@ExtendWith(SpringExtension.class)
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

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
                paymentRequest.getPayment().getAmount(),
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
                paymentRequest.getPayment().getAmount(),
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

    /**
     * Method under test: {@link PaymentService#chargeCard(UUID, PaymentRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testChargeCard() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at java.util.Objects.requireNonNull(Objects.java:208)
        //       at java.util.ImmutableCollections$List12.indexOf(ImmutableCollections.java:590)
        //       at java.util.ImmutableCollections$AbstractImmutableList.contains(ImmutableCollections.java:329)
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:36)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById((UUID) any())).thenReturn(ofResult);
        UUID customerId = UUID.randomUUID();
        paymentService.chargeCard(customerId, new PaymentRequest(new Payment()));
    }

    /**
     * Method under test: {@link PaymentService#chargeCard(UUID, PaymentRequest)}
     */
    @Test
    void testChargeCard2() {
        when(customerRepository.findById((UUID) any())).thenReturn(Optional.empty());
        UUID customerId = UUID.randomUUID();
        assertThrows(IllegalStateException.class,
                () -> paymentService.chargeCard(customerId, new PaymentRequest(new Payment())));
        verify(customerRepository).findById((UUID) any());
    }

    /**
     * Method under test: {@link PaymentService#chargeCard(UUID, PaymentRequest)}
     */
    @Test
    void testChargeCard3() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById((UUID) any())).thenReturn(ofResult);

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(42L));
        payment.setCurrency(Currency.USD);
        payment.setCustomerId(UUID.randomUUID());
        payment.setDescription("The characteristics of someone or something");
        payment.setPaymentId(123L);
        payment.setSource("Source");
        when(paymentRepository.save((Payment) any())).thenReturn(payment);
        when(cardPaymentCharger.chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any()))
                .thenReturn(new CardPaymentCharge(true));
        UUID randomUUIDResult = UUID.randomUUID();

        Payment payment1 = new Payment();
        payment1.setCurrency(Currency.USD);
        PaymentRequest paymentRequest = new PaymentRequest(payment1);
        paymentService.chargeCard(randomUUIDResult, paymentRequest);
        verify(customerRepository).findById((UUID) any());
        verify(paymentRepository).save((Payment) any());
        verify(cardPaymentCharger).chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any());
        assertSame(randomUUIDResult, paymentRequest.getPayment().getCustomerId());
    }

    /**
     * Method under test: {@link PaymentService#chargeCard(UUID, PaymentRequest)}
     */
    @Test
    void testChargeCard4() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById((UUID) any())).thenReturn(ofResult);
        when(paymentRepository.save((Payment) any())).thenThrow(new IllegalStateException());
        when(cardPaymentCharger.chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any()))
                .thenReturn(new CardPaymentCharge(true));
        UUID customerId = UUID.randomUUID();

        Payment payment = new Payment();
        payment.setCurrency(Currency.USD);
        assertThrows(IllegalStateException.class,
                () -> paymentService.chargeCard(customerId, new PaymentRequest(payment)));
        verify(customerRepository).findById((UUID) any());
        verify(paymentRepository).save((Payment) any());
        verify(cardPaymentCharger).chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any());
    }

    /**
     * Method under test: {@link PaymentService#chargeCard(UUID, PaymentRequest)}
     */
    @Test
    void testChargeCard5() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById((UUID) any())).thenReturn(ofResult);

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(42L));
        payment.setCurrency(Currency.USD);
        payment.setCustomerId(UUID.randomUUID());
        payment.setDescription("The characteristics of someone or something");
        payment.setPaymentId(123L);
        payment.setSource("Source");
        when(paymentRepository.save((Payment) any())).thenReturn(payment);
        when(cardPaymentCharger.chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any()))
                .thenReturn(new CardPaymentCharge(false));
        UUID customerId = UUID.randomUUID();

        Payment payment1 = new Payment();
        payment1.setCurrency(Currency.USD);
        assertThrows(IllegalStateException.class,
                () -> paymentService.chargeCard(customerId, new PaymentRequest(payment1)));
        verify(customerRepository).findById((UUID) any());
        verify(cardPaymentCharger).chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any());
    }

    /**
     * Method under test: {@link PaymentService#chargeCard(UUID, PaymentRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testChargeCard6() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.payment.CardPaymentCharge.isCardDebited()" because "cardPaymentCharge" is null
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:54)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById((UUID) any())).thenReturn(ofResult);

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(42L));
        payment.setCurrency(Currency.USD);
        payment.setCustomerId(UUID.randomUUID());
        payment.setDescription("The characteristics of someone or something");
        payment.setPaymentId(123L);
        payment.setSource("Source");
        when(paymentRepository.save((Payment) any())).thenReturn(payment);
        when(cardPaymentCharger.chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any()))
                .thenReturn(null);
        UUID customerId = UUID.randomUUID();

        Payment payment1 = new Payment();
        payment1.setCurrency(Currency.USD);
        paymentService.chargeCard(customerId, new PaymentRequest(payment1));
    }

    /**
     * Method under test: {@link PaymentService#chargeCard(UUID, PaymentRequest)}
     */
    @Test
    void testChargeCard7() {
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById((UUID) any())).thenReturn(ofResult);

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(42L));
        payment.setCurrency(Currency.USD);
        payment.setCustomerId(UUID.randomUUID());
        payment.setDescription("The characteristics of someone or something");
        payment.setPaymentId(123L);
        payment.setSource("Source");
        when(paymentRepository.save((Payment) any())).thenReturn(payment);
        when(cardPaymentCharger.chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any()))
                .thenReturn(new CardPaymentCharge(true));
        UUID customerId = UUID.randomUUID();

        Payment payment1 = new Payment();
        payment1.setCurrency(Currency.EUR);
        assertThrows(IllegalStateException.class,
                () -> paymentService.chargeCard(customerId, new PaymentRequest(payment1)));
        verify(customerRepository).findById((UUID) any());
    }

    /**
     * Method under test: {@link PaymentService#chargeCard(UUID, PaymentRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testChargeCard8() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.payment.PaymentRequest.getPayment()" because "paymentRequest" is null
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:36)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById((UUID) any())).thenReturn(ofResult);

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(42L));
        payment.setCurrency(Currency.USD);
        payment.setCustomerId(UUID.randomUUID());
        payment.setDescription("The characteristics of someone or something");
        payment.setPaymentId(123L);
        payment.setSource("Source");
        when(paymentRepository.save((Payment) any())).thenReturn(payment);
        when(cardPaymentCharger.chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any()))
                .thenReturn(new CardPaymentCharge(true));
        paymentService.chargeCard(UUID.randomUUID(), null);
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