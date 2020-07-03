package org.martynas.currencyconverter;

import org.martynas.currencyconverter.service.CurrencyExchangeService;

public class Main {

    // Get an instance of currencyExchangeService
    private static CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();

    public static void main(String[] args) {

        // Get from command line arguments 3 arguments - currency amount x to convert, from currency y to currency z
        String amount = args[0];
        String fromCurrency = args[1];
        String toCurrency = args[2];

        // Pass arguments to process the currency exchange and print result to STDOUT
        currencyExchangeService.convert(amount, fromCurrency, toCurrency);

    }


}
