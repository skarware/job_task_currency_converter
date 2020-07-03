package org.martynas.currencyconverter.service;

import org.martynas.currencyconverter.model.CurrencyRates;

public class CurrencyExchangeService {

    private final CurrencyRates currencyRates = new CurrencyRates();
    private final FileService fileService = new FileService();

    // On class instance instantiation initialize exchange rates data (Could be in constructor block too no reason to keep code here as initialized data is not needed before constructor)
    {
        this.initializeExchangeRatesData();
        System.out.println(currencyRates); //////////////////// FOR DEVELOPMENT PURPOSES ////////////////////
    }

    // Load currency rates data from a CSV file
    private void initializeExchangeRatesData() {

        // Pass an instance of Map<String, Currency> obj to be filled with currency exchange rates from a file
        boolean isDataLoadedSuccessfully = fileService.loadCurrencyRatesData(currencyRates);

        // If loading exchange rates data from a file fails inform the user and exit the program
        if (!isDataLoadedSuccessfully) {
            System.err.println("ERROR: Loading currency rates data from a file failed. Exiting the program...");
            System.exit(0);
        }
    }

    public void convert(String amount, String fromCurrency, String toCurrency) {

        System.out.println("amount: " + amount + "; fromCurrency: " + fromCurrency+ "; toCurrency: " + toCurrency); //////////////////// FOR DEVELOPMENT PURPOSES ////////////////////


    }
}
