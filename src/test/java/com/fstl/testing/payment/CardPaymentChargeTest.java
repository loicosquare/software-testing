package com.fstl.testing.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CardPaymentChargeTest {
    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link CardPaymentCharge#CardPaymentCharge(boolean)}
     *   <li>{@link CardPaymentCharge#toString()}
     *   <li>{@link CardPaymentCharge#isCardDebited()}
     * </ul>
     */
    @Test
    void testConstructor() {
        CardPaymentCharge actualCardPaymentCharge = new CardPaymentCharge(true);
        String actualToStringResult = actualCardPaymentCharge.toString();
        assertTrue(actualCardPaymentCharge.isCardDebited());
        assertEquals("CardPaymentCharge{isCardDebited=true}", actualToStringResult);
    }
}

