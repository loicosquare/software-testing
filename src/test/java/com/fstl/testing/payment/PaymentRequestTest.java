package com.fstl.testing.payment;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class PaymentRequestTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link PaymentRequest#PaymentRequest(Payment)}
     *   <li>{@link PaymentRequest#toString()}
     *   <li>{@link PaymentRequest#getPayment()}
     * </ul>
     */
    @Test
    void testConstructor() {
        Payment payment = new Payment();
        payment.setAmount(BigDecimal.valueOf(42L));
        payment.setCurrency(Currency.USD);
        payment.setCustomerId(UUID.randomUUID());
        payment.setDescription("The characteristics of someone or something");
        payment.setPaymentId(123L);
        payment.setSource("Source");
        PaymentRequest actualPaymentRequest = new PaymentRequest(payment);
        actualPaymentRequest.toString();
        assertSame(payment, actualPaymentRequest.getPayment());
    }
}

