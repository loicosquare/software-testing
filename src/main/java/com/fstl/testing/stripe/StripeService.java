package com.fstl.testing.stripe;

import com.fstl.testing.payment.CardPaymentCharge;
import com.fstl.testing.payment.CardPaymentCharger;
import com.fstl.testing.payment.Currency;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService implements CardPaymentCharger {

    private final static RequestOptions requestOptions = RequestOptions.builder()
            .setApiKey("sk_test_4eC39HqLyjWDarjtT1zdp7dc")
            .build();

    @Override
    public CardPaymentCharge chargeCard(String cardSource, String amount, Currency currency, String description) {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", 2000);
        params.put("currency", "currency");
        params.put("source", cardSource);
        params.put("description",description);

        try {
            Charge charge = Charge.create(params, requestOptions);
            return new CardPaymentCharge(charge.getPaid());
        } catch (StripeException e) {
            throw new IllegalStateException("Cannot make stripe charge", e);
        }
    }
}
