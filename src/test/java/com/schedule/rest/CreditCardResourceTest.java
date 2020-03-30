package com.schedule.rest;

import com.schedule.dto.CreditCardDTO;
import com.schedule.service.CreditCardService;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class CreditCardResourceTest {

    @Inject
    private CreditCardService service;

    @Test
    public void testNewCreditCard() {
        CreditCardDTO creditCard = new CreditCardDTO();
        creditCard.setCardNumber(18273645);
        creditCard.setSecurityCode(456);
        creditCard.setOwnerName("USUARIO CARTAO");
        creditCard.setEmissionDate("2019-01-01");
        creditCard.setValidThru("2024-05-01");
        given().when().contentType("application/json").body(creditCard).post("/credit-cards").then().statusCode(200);
    }

}
