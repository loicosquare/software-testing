package com.fstl.testing.stripe;

import com.fstl.testing.payment.CardPaymentCharge;
import com.fstl.testing.payment.CardPaymentCharger;
import com.fstl.testing.payment.Currency;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@ConditionalOnProperty( //String w'll use this property if the stripe.enabled having false value [see application.properties]
        value = "stripe.enabled",
        havingValue = "false"
)
public class MockStripeService implements CardPaymentCharger {

    @Override
    public CardPaymentCharge chargeCard(String cardSource,
                                        BigDecimal amount,
                                        Currency currency,
                                        String description) {
        return new CardPaymentCharge(true);
    }
}
