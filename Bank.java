package org.poo.cb;

import java.util.*;

public class Bank {
    static List<User> users = new ArrayList<>();
    static List<String> currencies = new ArrayList<>();
    static List<Exchanges> exchanges = new ArrayList<>();
    static List<Stocks> stocks = new ArrayList<>();

    private static Bank bankInstance;
    private Bank() {}

    // singleton implementation
    public static Bank Instance() {
        if (bankInstance == null)
            bankInstance = new Bank();
        return bankInstance;
    }
}
