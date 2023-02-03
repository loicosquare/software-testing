package com.fstl.testing.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstl.testing.customer.Customer;
import com.fstl.testing.customer.CustomerRegistrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentIntegrationTest {

    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    void itShouldCreatePaymentSuccessfully() throws Exception {
        //Given a customer
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "James", "+447000000000");
        CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest(customer);

        //Regsiter
        ResultActions customerRegResultActions = mockMvc.perform(put("/api/v1/customer-registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(customerRegistrationRequest))
        );
        //System.out.println(customerRegResultActions);

        //Payment request
        long paymentId = 1L;
        Payment payment = new Payment(paymentId,
                customerId,
                new BigDecimal("100.00"),
                Currency.GBP,
                "x0x0x0x0",
                "Zakat");
        PaymentRequest paymentRequest = new PaymentRequest(payment);


        //When payment is sent
        ResultActions paymentResultActions = mockMvc.perform(post("/api/v1/payment/makePayment", customerId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(paymentRequest))
        );

        //Then both customer registration and payment requests are 200 status
        customerRegResultActions.andExpect(status().isOk());
        paymentResultActions.andExpect(status().isOk());

        //payment is stored in db
        //TODO: Do no use paymentRepository instead create and endpoint to retrieve payments for customers Because in entregration Test we don't use Repository (@Autowired) only MockMvc
        assertThat(paymentRepository.findById(paymentId))
                .isPresent()
                .hasValueSatisfying(p-> assertThat(p).isEqualToComparingFieldByField(payment));

        //TODO: Ensure smsm is delivered
    }

    private String objectToJson(Object object) {
        try{
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            //throw new RuntimeException(e);
            fail("Failed to convert object to json");
            return null;
        }
    }
}
