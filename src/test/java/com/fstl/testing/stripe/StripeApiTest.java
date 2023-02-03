package com.fstl.testing.stripe;

import com.stripe.exception.StripeException;
import com.stripe.net.RequestOptions;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {StripeApi.class})
@ExtendWith(SpringExtension.class)
class StripeApiTest {
    @Autowired
    private StripeApi stripeApi;

    /**
     * Method under test: {@link StripeApi#create(Map, RequestOptions)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreate() throws StripeException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.stripe.exception.AuthenticationException: No API key provided. Set your API key using `Stripe.apiKey = "<API-KEY>"`. You can generate API keys from the Stripe Dashboard. See https://stripe.com/docs/api/authentication for details or contact support at https://support.stripe.com/email if you have any questions.
        //       at com.stripe.net.StripeRequest.buildHeaders(StripeRequest.java:152)
        //       at com.stripe.net.StripeRequest.<init>(StripeRequest.java:75)
        //       at com.stripe.net.LiveStripeResponseGetter.request(LiveStripeResponseGetter.java:53)
        //       at com.stripe.net.ApiResource.request(ApiResource.java:179)
        //       at com.stripe.model.Charge.create(Charge.java:629)
        //       at com.fstl.testing.stripe.StripeApi.create(StripeApi.java:14)
        //   See https://diff.blue/R013 to resolve this issue.

        HashMap<String, Object> requestMap = new HashMap<>();
        stripeApi.create(requestMap, RequestOptions.getDefault());
    }

    /**
     * Method under test: {@link StripeApi#create(Map, RequestOptions)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreate2() throws StripeException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.stripe.exception.AuthenticationException: No API key provided. Set your API key using `Stripe.apiKey = "<API-KEY>"`. You can generate API keys from the Stripe Dashboard. See https://stripe.com/docs/api/authentication for details or contact support at https://support.stripe.com/email if you have any questions.
        //       at com.stripe.net.StripeRequest.buildHeaders(StripeRequest.java:152)
        //       at com.stripe.net.StripeRequest.<init>(StripeRequest.java:75)
        //       at com.stripe.net.LiveStripeResponseGetter.request(LiveStripeResponseGetter.java:53)
        //       at com.stripe.net.ApiResource.request(ApiResource.java:179)
        //       at com.stripe.model.Charge.create(Charge.java:629)
        //       at com.fstl.testing.stripe.StripeApi.create(StripeApi.java:14)
        //   See https://diff.blue/R013 to resolve this issue.

        HashMap<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("Key", "Value");
        stripeApi.create(stringObjectMap, RequestOptions.getDefault());
    }

    /**
     * Method under test: {@link StripeApi#create(Map, RequestOptions)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testCreate3() throws StripeException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.stripe.exception.AuthenticationException: No API key provided. Set your API key using `Stripe.apiKey = "<API-KEY>"`. You can generate API keys from the Stripe Dashboard. See https://stripe.com/docs/api/authentication for details or contact support at https://support.stripe.com/email if you have any questions.
        //       at com.stripe.net.StripeRequest.buildHeaders(StripeRequest.java:152)
        //       at com.stripe.net.StripeRequest.<init>(StripeRequest.java:75)
        //       at com.stripe.net.LiveStripeResponseGetter.request(LiveStripeResponseGetter.java:53)
        //       at com.stripe.net.ApiResource.request(ApiResource.java:179)
        //       at com.stripe.model.Charge.create(Charge.java:629)
        //       at com.fstl.testing.stripe.StripeApi.create(StripeApi.java:14)
        //   See https://diff.blue/R013 to resolve this issue.

        stripeApi.create(new HashMap<>(), null);
    }
}

