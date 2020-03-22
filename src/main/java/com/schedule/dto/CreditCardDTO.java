package com.schedule.dto;

public class CreditCardDTO {

    private int cardNumber;
    private int securityCode;
    private String ownerName;
    private String emissionDate;
    private String validThru;

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(int securityCode) {
        this.securityCode = securityCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(String emissionDate) {
        this.emissionDate = emissionDate;
    }

    public String getValidThru() {
        return validThru;
    }

    public void setValidThru(String validThru) {
        this.validThru = validThru;
    }
}
