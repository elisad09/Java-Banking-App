package org.poo.cb;

import java.util.*;

public class User {
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private int noStocks;
    List<Account> accounts;
    List<Stocks> stocks;
    List<User> friends;

    User(UserBuilder builder) {
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.address = builder.address;
        this.accounts = new ArrayList<>();
        this.stocks = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public static class UserBuilder {
        private String email, firstName, lastName, address;

        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withfirstName(String firstname) {
            this.firstName = firstname;
            return this;
        }

        public UserBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    public static boolean userExists(String email) {
        for (User user : Bank.users) {
            if (user.email.equals(email)) return true;
        }
        return false;
    }

    public void printUserInfo() {
        String info = "{\"email\":\"" + email +
                "\",\"firstname\":\"" + firstName +
                "\",\"lastname\":\"" + lastName +
                "\",\"address\":\"" + address +
                "\",\"friends\":[";

        if (friends != null) {
            for (User friend : friends) {
                info = info.concat("\"" + friend.getEmail() + "\"");
            }

        }
        info = info.concat("]}");
        System.out.println(info);

    }

    public void printPortfolio() {
        String info = "{\"stocks\":[";
        if (!stocks.isEmpty()) {
            for (int i = 0; i < stocks.size() - 1; i++) {
                info += "{\"stockName\":\"" + stocks.get(i).getCompany() + "\",\"amount\":" + stocks.get(i).getNoOfStocks() + "},";
            }
            info += "{\"stockName\":\"" + stocks.get(stocks.size() - 1).getCompany() + "\",\"amount\":" + stocks.get(stocks.size() - 1).getNoOfStocks() + "}";
        }

        info += "],\"accounts\":[";

        if (!accounts.isEmpty()) {
            for (int i = 0; i < accounts.size() - 1; i++) {
                info += "{\"currencyName\":\"" + accounts.get(i).currency + "\",\"amount\":\"" + String.format("%.2f", accounts.get(i).getAmount()) + "\"},";
            }
            info += "{\"currencyName\":\"" + accounts.get(accounts.size() - 1).currency + "\",\"amount\":\"" + String.format("%.2f", accounts.get(accounts.size() - 1).getAmount()) + "\"}";
        }
        info += "]}";

        System.out.println(info);
    }

    public boolean userIsAlreadyFriend(String email) {
        for (User user : friends) {
            if(email.equals(user.getEmail())) {
                return true;
            }
        }
        return false;
    }

    public static User getUserbyEmail(String email) {
        for (User user : Bank.users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public Account getUserAccountByCurrency(String currency) {
        for (Account acc : accounts) {
            if (acc.currency.equals(currency)) {
                return acc;
            }
        }
        return null;
    }
}
