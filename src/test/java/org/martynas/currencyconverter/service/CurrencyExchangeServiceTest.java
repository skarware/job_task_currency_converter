package org.martynas.currencyconverter.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.martynas.currencyconverter.model.CurrencyRates;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.martynas.currencyconverter.service.CurrencyExchangeService.*;

class CurrencyExchangeServiceTest {

    private static final CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService();

    @Test
    void givenValidArgs_whenConvertPrintToSTDOUT_thenDoesNotThrow() {
        String[] args = new String[]{"2", "USD", "EUR"};
        Executable executable = () -> currencyExchangeService.convertPrintToSTDOUT(args[0], args[1], args[2]);
        assertDoesNotThrow(executable);
    }

    @Test
    void givenNotEnoughArgs_whenConvertPrintToSTDOUT_whenAssertingException_thenThrown() {
        String[] args = new String[]{"2", "USD"};
        Executable executable = () -> currencyExchangeService.convertPrintToSTDOUT(args[0], args[1], args[2]);
        assertThrows(ArrayIndexOutOfBoundsException.class, executable);
    }

    @Test
    void givenNotValidArgs_whenConvertPrintToSTDOUT_whenAssertingException_thenThrown() {
        String[] args = new String[]{"2aa2", "USD", "USD"};
        Executable executable = () -> currencyExchangeService.convertPrintToSTDOUT(args[0], args[1], args[2]);
        assertThrows(NumberFormatException.class, executable);
    }

    final CurrencyRates currencyRates = new CurrencyRates();
    final FileService fileService = new FileService();

    {
        boolean isDataLoadedSuccessfully = fileService.loadCurrencyRatesData(currencyRates);
        if (!isDataLoadedSuccessfully) {
            System.err.println("ERROR: Loading currency rates data from a file failed. Aborting the test...");
            System.exit(0);
        }
    }

    @Test
    void given3EURUSD_whenConvertCurrency_thenExpectResult() {

        BigDecimal convertedAmount = convertCurrency(
                new BigDecimal("3"),
                "EUR",
                "USD",
                currencyRates,
                0
        );
        assertEquals("3.705750000554009625", convertedAmount.toString());
    }

    @Test
    void given2USDGBP_whenConvertCurrency_thenExpectResult() {

        BigDecimal convertedAmount = convertCurrency(
                new BigDecimal("2"),
                "USD",
                "GBP",
                currencyRates,
                0
        );
        assertEquals("1.437039699297502873", convertedAmount.toString());
    }

    @Test
    void given0USDEUR_whenConvertCurrency_thenExpectResult() {

        BigDecimal convertedAmount = convertCurrency(
                new BigDecimal("0"),
                "USD",
                "EUR",
                currencyRates,
                0
        );
        assertEquals("0E-18", convertedAmount.toString());
    }

    @Test
    void givenInvalidNumberForAmount_whenConvertCurrency_whenAssertingException_thenThrown() {
        Throwable exception = assertThrows(
                NumberFormatException.class,
                () -> convertCurrency(
                        new BigDecimal("12abc3"),
                        "USD",
                        "EUR",
                        currencyRates,
                        0
                )
        );
    }

    @Test
    void givenSameCurrencies_whenCalcConversionRatio_thenExpectResult() {
        BigDecimal conversionRate = calcConversionRatio(currencyRates.getCurrency("USD"), currencyRates.getCurrency("USD"));
        assertEquals("1", conversionRate.toString());
    }

    @Test
    void givenNull_whenCalcConversionRatio_whenAssertingException_thenThrown() {
        Executable executable = () -> calcConversionRatio(null, currencyRates.getCurrency("USD"));
        Exception exception = assertThrows(NullPointerException.class, executable);
    }

    @Test
    void givenOneOne_whenCalcExchangeFee_thenExpectResult() {
        BigDecimal exchangeFee = calcExchangeFee(BigDecimal.valueOf(1), 1);
        assertEquals("1.0", exchangeFee.toString());
    }

    @Test
    void givenZeroOne_whenCalcExchangeFee_thenExpectResult() {
        BigDecimal exchangeFee = calcExchangeFee(BigDecimal.valueOf(0), 1);
        assertEquals("0.0", exchangeFee.toString());
    }

    @Test
    void givenZeroZero_whenCalcExchangeFee_thenExpectResult() {
        BigDecimal exchangeFee = calcExchangeFee(BigDecimal.valueOf(0), 0);
        assertEquals("0.0", exchangeFee.toString());
    }

}