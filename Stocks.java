package org.poo.cb;

import java.util.*;

public class Stocks {
    private String company;
    private int noOfStocks;
    double[] values = new double[10];

    public Stocks(String company) {
        this.company = company;
    }

    public String getCompany() {
        return company;
    }

    public int getNoOfStocks() {
        return noOfStocks;
    }

    public void setNoOfStocks(int noOfStocks) {
        this.noOfStocks = noOfStocks;
    }

    public void updateNoOfStocks(int number) {
        this.noOfStocks += number;
    }

    public static Stocks getStockByCompany(String company) {
        for (Stocks st : Bank.stocks) {
            if (st.company.equals(company)) {
                return st;
            }
        }
        return null;
    }

    public boolean recommendedStock() {
        double shortTermSMA = 0;
        double longTermSMA = 0;
        for (int i = values.length - 1; i >= 0; i--) {
            if (i >= 5) {
                shortTermSMA += values[i];
            }
            longTermSMA += values[i];
        }
        shortTermSMA = shortTermSMA / 5;
        longTermSMA = longTermSMA / 10;

        return shortTermSMA > longTermSMA;
    }
}
