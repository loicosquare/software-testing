package com.fstl.testing.payment;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fstl.testing.customer.Customer;
import com.fstl.testing.customer.CustomerRepository;

import java.math.BigDecimal;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class PaymentControllerTest {
    /**
     * Method under test: {@link PaymentController#makePayment(PaymentRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMakePayment() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.customer.CustomerRepository.findById(Object)" because "this.customerRepository" is null
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:30)
        //       at com.fstl.testing.payment.PaymentController.makePayment(PaymentController.java:19)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:660)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:741)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at java.util.Objects.requireNonNull(Objects.java:208)
        //       at java.util.ImmutableCollections$List12.indexOf(ImmutableCollections.java:590)
        //       at java.util.ImmutableCollections$AbstractImmutableList.contains(ImmutableCollections.java:329)
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:36)
        //       at com.fstl.testing.payment.PaymentController.makePayment(PaymentController.java:19)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById((UUID) any())).thenReturn(Optional.of(customer));
        PaymentController paymentController = new PaymentController(
                new PaymentService(customerRepository, mock(PaymentRepository.class), mock(CardPaymentCharger.class)));
        paymentController.makePayment(new PaymentRequest(new Payment()));
    }

    /**
     * Method under test: {@link PaymentController#makePayment(PaymentRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMakePayment2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.customer.CustomerRepository.findById(Object)" because "this.customerRepository" is null
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:30)
        //       at com.fstl.testing.payment.PaymentController.makePayment(PaymentController.java:19)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:660)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:741)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalStateException: Customer with id [null] not found
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:32)
        //       at com.fstl.testing.payment.PaymentController.makePayment(PaymentController.java:19)
        //   See https://diff.blue/R013 to resolve this issue.

        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById((UUID) any())).thenReturn(Optional.empty());
        PaymentController paymentController = new PaymentController(
                new PaymentService(customerRepository, mock(PaymentRepository.class), mock(CardPaymentCharger.class)));
        paymentController.makePayment(new PaymentRequest(new Payment()));
    }

    /**
     * Method under test: {@link PaymentController#makePayment(PaymentRequest)}
     */
    @Test
    void testMakePayment3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.customer.CustomerRepository.findById(Object)" because "this.customerRepository" is null
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:30)
        //       at com.fstl.testing.payment.PaymentController.makePayment(PaymentController.java:19)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:660)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:741)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById((UUID) any())).thenReturn(Optional.of(customer));

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(42L));
        payment.setCurrency(Currency.USD);
        payment.setCustomerId(UUID.randomUUID());
        payment.setDescription("The characteristics of someone or something");
        payment.setPaymentId(123L);
        payment.setSource("Source");
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        when(paymentRepository.save((Payment) any())).thenReturn(payment);
        CardPaymentCharger cardPaymentCharger = mock(CardPaymentCharger.class);
        when(cardPaymentCharger.chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any()))
                .thenReturn(new CardPaymentCharge(true));
        PaymentController paymentController = new PaymentController(
                new PaymentService(customerRepository, paymentRepository, cardPaymentCharger));

        Payment payment1 = new Payment();
        payment1.setCurrency(Currency.USD);
        PaymentRequest paymentRequest = new PaymentRequest(payment1);
        paymentController.makePayment(paymentRequest);
        verify(customerRepository).findById((UUID) any());
        verify(paymentRepository).save((Payment) any());
        verify(cardPaymentCharger).chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any());
        assertNull(paymentRequest.getPayment().getCustomerId());
    }

    /**
     * Method under test: {@link PaymentController#makePayment(PaymentRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMakePayment4() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.customer.CustomerRepository.findById(Object)" because "this.customerRepository" is null
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:30)
        //       at com.fstl.testing.payment.PaymentController.makePayment(PaymentController.java:19)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:660)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:741)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalStateException: Card not debited for customer null
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:55)
        //       at com.fstl.testing.payment.PaymentController.makePayment(PaymentController.java:19)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById((UUID) any())).thenReturn(Optional.of(customer));

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(42L));
        payment.setCurrency(Currency.USD);
        payment.setCustomerId(UUID.randomUUID());
        payment.setDescription("The characteristics of someone or something");
        payment.setPaymentId(123L);
        payment.setSource("Source");
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        when(paymentRepository.save((Payment) any())).thenReturn(payment);
        CardPaymentCharger cardPaymentCharger = mock(CardPaymentCharger.class);
        when(cardPaymentCharger.chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any()))
                .thenReturn(new CardPaymentCharge(false));
        PaymentController paymentController = new PaymentController(
                new PaymentService(customerRepository, paymentRepository, cardPaymentCharger));

        Payment payment1 = new Payment();
        payment1.setCurrency(Currency.USD);
        paymentController.makePayment(new PaymentRequest(payment1));
    }

    /**
     * Method under test: {@link PaymentController#makePayment(PaymentRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMakePayment5() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.customer.CustomerRepository.findById(Object)" because "this.customerRepository" is null
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:30)
        //       at com.fstl.testing.payment.PaymentController.makePayment(PaymentController.java:19)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:660)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:741)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.payment.CardPaymentCharge.isCardDebited()" because "cardPaymentCharge" is null
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:54)
        //       at com.fstl.testing.payment.PaymentController.makePayment(PaymentController.java:19)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById((UUID) any())).thenReturn(Optional.of(customer));

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(42L));
        payment.setCurrency(Currency.USD);
        payment.setCustomerId(UUID.randomUUID());
        payment.setDescription("The characteristics of someone or something");
        payment.setPaymentId(123L);
        payment.setSource("Source");
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        when(paymentRepository.save((Payment) any())).thenReturn(payment);
        CardPaymentCharger cardPaymentCharger = mock(CardPaymentCharger.class);
        when(cardPaymentCharger.chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any()))
                .thenReturn(null);
        PaymentController paymentController = new PaymentController(
                new PaymentService(customerRepository, paymentRepository, cardPaymentCharger));

        Payment payment1 = new Payment();
        payment1.setCurrency(Currency.USD);
        paymentController.makePayment(new PaymentRequest(payment1));
    }

    /**
     * Method under test: {@link PaymentController#makePayment(PaymentRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMakePayment6() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.customer.CustomerRepository.findById(Object)" because "this.customerRepository" is null
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:30)
        //       at com.fstl.testing.payment.PaymentController.makePayment(PaymentController.java:19)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:660)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:741)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalStateException: Currency [EUR] not supported
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:42)
        //       at com.fstl.testing.payment.PaymentController.makePayment(PaymentController.java:19)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById((UUID) any())).thenReturn(Optional.of(customer));

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(42L));
        payment.setCurrency(Currency.USD);
        payment.setCustomerId(UUID.randomUUID());
        payment.setDescription("The characteristics of someone or something");
        payment.setPaymentId(123L);
        payment.setSource("Source");
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        when(paymentRepository.save((Payment) any())).thenReturn(payment);
        CardPaymentCharger cardPaymentCharger = mock(CardPaymentCharger.class);
        when(cardPaymentCharger.chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any()))
                .thenReturn(new CardPaymentCharge(true));
        PaymentController paymentController = new PaymentController(
                new PaymentService(customerRepository, paymentRepository, cardPaymentCharger));

        Payment payment1 = new Payment();
        payment1.setCurrency(Currency.EUR);
        paymentController.makePayment(new PaymentRequest(payment1));
    }

    /**
     * Method under test: {@link PaymentController#makePayment(PaymentRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testMakePayment7() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.customer.CustomerRepository.findById(Object)" because "this.customerRepository" is null
        //       at com.fstl.testing.payment.PaymentService.chargeCard(PaymentService.java:30)
        //       at com.fstl.testing.payment.PaymentController.makePayment(PaymentController.java:19)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:660)
        //       at javax.servlet.http.HttpServlet.service(HttpServlet.java:741)
        //   See https://diff.blue/R013 to resolve this issue.

        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "com.fstl.testing.payment.PaymentRequest.getPayment()" because "paymentRequest" is null
        //       at com.fstl.testing.payment.PaymentController.makePayment(PaymentController.java:19)
        //   See https://diff.blue/R013 to resolve this issue.

        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Name");
        customer.setPhoneNumber("4105551212");
        CustomerRepository customerRepository = mock(CustomerRepository.class);
        when(customerRepository.findById((UUID) any())).thenReturn(Optional.of(customer));

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(42L));
        payment.setCurrency(Currency.USD);
        payment.setCustomerId(UUID.randomUUID());
        payment.setDescription("The characteristics of someone or something");
        payment.setPaymentId(123L);
        payment.setSource("Source");
        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        when(paymentRepository.save((Payment) any())).thenReturn(payment);
        CardPaymentCharger cardPaymentCharger = mock(CardPaymentCharger.class);
        when(cardPaymentCharger.chargeCard((String) any(), (BigDecimal) any(), (Currency) any(), (String) any()))
                .thenReturn(new CardPaymentCharge(true));
        (new PaymentController(new PaymentService(customerRepository, paymentRepository, cardPaymentCharger)))
                .makePayment(null);
    }
}

