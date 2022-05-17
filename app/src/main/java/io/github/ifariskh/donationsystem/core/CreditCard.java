package io.github.ifariskh.donationsystem.core;

public class CreditCard implements IPayment{

    private String number;
    private String cvc;
    private String name;
    private String expireDate;

    public CreditCard(String number, String cvc, String name, String expireDate) {
        this.number = number;
        this.cvc = cvc;
        this.name = name;
        this.expireDate = expireDate;
    }

    public String getNumber() {
        return number;
    }

    public String getCvc() {
        return cvc;
    }

    public String getName() {
        return name;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public boolean pay() {
        return false;
    }
}
