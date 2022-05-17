package io.github.ifariskh.donationsystem.core;

import java.util.ArrayList;

public class Wallet implements IPayment{
    private double balance;
    private ArrayList<CreditCard> cards;

    public Wallet(double balance, ArrayList<CreditCard> cards) {
        this.balance = balance;
        this.cards = cards;
    }

    @Override
    public boolean pay() {
        return false;
    }

    public boolean addCard(CreditCard card){
        this.cards.add(card);
        return true;
    }

    public boolean removeCard(CreditCard card){
        cards.remove(card);
        return true;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public ArrayList<CreditCard> getCards() {
        return cards;
    }

    public void setCards(ArrayList<CreditCard> cards) {
        this.cards = cards;
    }
}
