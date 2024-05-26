package org.poo.cb;

import java.util.HashMap;
import java.util.Map;

public class Exchanges {
    String sourceCurrency;
    HashMap<String, Double> converted = new HashMap<String, Double>();
    public Exchanges(String currency) {
        this.sourceCurrency = currency;
    }
}
