package org.martynas.currencyconverter;

import org.martynas.currencyconverter.service.CurrencyExchangeService;

public class Main {

    // Get an instance of currencyExchangeService
    private static final CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();

    public static void main(String[] args) {
        /**
         * Get three arguments from command line: currency amount args[0] to convert, from currency args[1], to currency args[2]
         * and pass them to currencyExchangeService to process the currency converting and print result to STDOUT
         */
        currencyExchangeService.convert(args[0], args[1], args[2]);

    }


}
