package org.martynas.currencyconverter.service;

import org.martynas.currencyconverter.model.CurrencyRates;

public class CurrencyExchangeService {

    private final CurrencyRates currencyRates = new CurrencyRates();

    public void convert(String amount, String fromCurrency, String toCurrency) {

        System.out.println("amount: " + amount + "; fromCurrency: " + fromCurrency+ "; toCurrency: " + toCurrency); //////////////////// FOR DEVELOPMENT PURPOSES ////////////////////

    }
}
