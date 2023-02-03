package com.fstl.testing.payment;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fstl.testing.customer.Customer;
import com.fstl.testing.customer.CustomerRepository;

import java.math.BigDecimal;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PaymentService.class})
@ExtendWith(SpringExtension.class)
class PaymentServiceDiffBlueTest {
    @MockBean
    private CardPaymentCharger cardPaymentCharger;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

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
    @Disabled("TODO: Complete this test")
    void testChargeCard7() {
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
}

