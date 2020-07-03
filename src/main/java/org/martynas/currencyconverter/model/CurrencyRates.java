package org.martynas.currencyconverter.model;

import java.util.HashMap;
import java.util.Map;

public class CurrencyRates {

    private final Map<String, Currency> currencies = new HashMap<>();

    public int getSize() {
        return this.currencies.size();
    }

    public boolean addCurrency(Currency currency) {
        try {
            this.currencies.put(currency.getName(), currency);
            return true;
        } catch (RuntimeException exc) {
            System.err.println("ERROR: Failed to add new Currency obj into CurrencyRates map");
            return false;
        }
    }

    @Override
    public String toString() {
        return "CurrencyRates{" +
                "currencies=" + currencies +
                '}';
    }
}
