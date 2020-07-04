package org.martynas.currencyconverter.model;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class CurrencyRates {

    private final Map<String, Currency> currencies = new HashMap<>();

    public int getSize() {
        return this.currencies.size();
    }

    // Some additional layer of abstraction in case adding currency logic changes in future
    public boolean addCurrency(Currency currency) {
        try {
            this.currencies.put(currency.getName(), currency);
            return true;
        } catch (RuntimeException exc) {
            System.err.println("ERROR: Failed to add new Currency obj into CurrencyRates map");
            return false;
        }
    }

    public Currency getCurrency(String currencyName) {
        Currency currency = this.currencies.get(currencyName);
        if (currency == null) {
            throw new NoSuchElementException("Currency not found.");
        }
        return currency;
    }

    @Override
    public String toString() {
        return "CurrencyRates{" +
                "currencies=" + currencies +
                '}';
    }
}
