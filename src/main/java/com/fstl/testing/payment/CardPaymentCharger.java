package com.fstl.testing.payment;

public interface CardPaymentCharger {

    CardPaymentCharge chargeCard(
            String source,
            String amount,
            Currency currency,
            String description
    );
}
