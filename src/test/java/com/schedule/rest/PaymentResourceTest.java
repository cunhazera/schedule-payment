package com.schedule.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.schedule.dto.CreditCardDTO;
import com.schedule.dto.PaymentDTO;
import com.schedule.entity.Currency;
import com.schedule.entity.Payment;
import com.schedule.service.PaymentService;
import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class PaymentResourceTest {

    @Inject
    private PaymentService service;

    @Test
    public void testNewPayment() {
        CreditCardDTO creditCard = new CreditCardDTO();
        creditCard.setCardNumber(43214321);
        creditCard.setSecurityCode(123);
        creditCard.setOwnerName("USUARIO");
        creditCard.setEmissionDate("2019-01-01");
        creditCard.setValidThru("2024-05-01");
        given().when().contentType("application/json").body(creditCard).post("/credit-cards").then().statusCode(200);
        PaymentDTO payment = new PaymentDTO();
        payment.setDueDate("2030-03-03 12:30:30");
        payment.setProductId("123");
        payment.setCustomerMail("cunha.gabriel919@gmail.com");
        payment.setAmount(50);
        payment.setCreditCard(creditCard.getCardNumber());
        payment.setCurrency(Currency.BLR);
        given().contentType("application/json")
                .body(payment)
                .when()
                .post("/payments")
                .then()
                .body("currency", is(payment.getCurrency().name()))
                .body("productId", is(payment.getProductId()))
                .body("creditCard.cardNumber", is(payment.getCreditCard()))
                .statusCode(200);
    }

    // @Test
    public void testFindPaymentById() throws JsonProcessingException {
        CreditCardDTO creditCard = new CreditCardDTO();
        creditCard.setCardNumber(1);
        creditCard.setSecurityCode(123);
        creditCard.setOwnerName("USUARIO CARTAO");
        creditCard.setEmissionDate("2019-01-01");
        creditCard.setValidThru("2024-05-01");
        given().contentType("application/json").body(creditCard).post("/credit-cards").then().statusCode(200);
        PaymentDTO payment = new PaymentDTO();
        payment.setDueDate("2030-03-03 12:30:30");
        payment.setProductId("1234");
        payment.setCustomerMail("cunha.gabriel919@gmail.com");
        payment.setAmount(50);
        payment.setCreditCard(creditCard.getCardNumber());
        payment.setCurrency(Currency.BLR);

        String response = given().contentType("application/json")
                .body(payment)
                .when()
                .post("/payments").asString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        Payment obj = mapper.readValue(response, Payment.class);

        given().get(String.format("/payments/%d", obj.id)).then()
                .body("id", Matchers.greaterThan(0))
                .body("creditCard.cardNumber", is(creditCard.getCardNumber()))
                .body("amount", is(payment.getAmount()));
    }

    //@Test
    public void testNoCreditCard() {
        PaymentDTO payment = new PaymentDTO();
        payment.setDueDate("2030-03-03 12:30:30");
        payment.setProductId("123");
        payment.setCustomerMail("cunha.gabriel919@gmail.com");
        payment.setAmount(50);
        payment.setCreditCard(123);
        payment.setCurrency(Currency.BLR);
        given().contentType("application/json")
                .body(payment)
                .when()
                .post("/payments")
                .then()
                .statusCode(404)
                .body(is("No credit card with number: 123"));
    }

}
