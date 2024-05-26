package org.poo.cb;

public class Account {
    String currency;
    private double amount;

    public Account(String currency) {
        this.currency = currency;
        amount = 0;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void increaseAmount(double amount) {
        this.amount += amount;
    }

    public void decreaseAmount(double amount) {
        this.amount -= amount;
    }
}
