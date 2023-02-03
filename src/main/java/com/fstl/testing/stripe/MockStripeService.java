package com.fstl.testing.stripe;

import com.fstl.testing.payment.CardPaymentCharge;
import com.fstl.testing.payment.CardPaymentCharger;
import com.fstl.testing.payment.Currency;

import java.math.BigDecimal;

public class MockStripeService implements CardPaymentCharger {

    @Override
    public CardPaymentCharge chargeCard(String source, BigDecimal amount, Currency currency, String description) {
        return null;
    }
}
