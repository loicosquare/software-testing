package com.fstl.testing.stripe;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fstl.testing.payment.Currency;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class MockStripeServiceTest {
    /**
     * Method under test: {@link MockStripeService#chargeCard(String, BigDecimal, Currency, String)}
     */
    @Test
    void testChargeCard() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       com.fstl.testing.stripe.MockStripeService
        //   See https://diff.blue/R027 to resolve this issue.

        MockStripeService mockStripeService = new MockStripeService();
        assertTrue(mockStripeService
                .chargeCard("Card Source", BigDecimal.valueOf(42L), Currency.USD, "The characteristics of someone or something")
                .isCardDebited());
    }

    /**
     * Method under test: {@link MockStripeService#chargeCard(String, BigDecimal, Currency, String)}
     */
    @Test
    void testChargeCard2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       com.fstl.testing.stripe.MockStripeService
        //   See https://diff.blue/R027 to resolve this issue.

        MockStripeService mockStripeService = new MockStripeService();
        assertTrue(
                mockStripeService
                        .chargeCard("Card Source", BigDecimal.valueOf(42L), Currency.GBP,
                                "The characteristics of someone or something")
                        .isCardDebited());
    }

    /**
     * Method under test: {@link MockStripeService#chargeCard(String, BigDecimal, Currency, String)}
     */
    @Test
    void testChargeCard3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R027 Missing beans when creating Spring context.
        //   Failed to create Spring context due to missing beans
        //   in the current Spring profile:
        //       com.fstl.testing.stripe.MockStripeService
        //   See https://diff.blue/R027 to resolve this issue.

        MockStripeService mockStripeService = new MockStripeService();
        assertTrue(
                mockStripeService
                        .chargeCard("Card Source", BigDecimal.valueOf(42L), Currency.EUR,
                                "The characteristics of someone or something")
                        .isCardDebited());
    }
}

