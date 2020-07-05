package org.martynas.currencyconverter.service;

import org.martynas.currencyconverter.model.Currency;
import org.martynas.currencyconverter.model.CurrencyRates;
import org.martynas.currencyconverter.util.NumberUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;

public class CurrencyExchangeService {

    // Rounding mode and scale for final calculation results
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    private static final int SCALE = 18;

    // MathContext for intermediate calculations
    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;

    private final CurrencyRates currencyRates = new CurrencyRates();
    private final FileService fileService = new FileService();

    // On class instance instantiation initialize conversion rates
    public CurrencyExchangeService() {
        this.initializeExchangeRatesData();
    }

    // Load currency rates data from a CSV file
    private void initializeExchangeRatesData() {
        // Pass an instance of CurrencyRates obj to be filled with currency exchange rates from a file
        boolean isDataLoadedSuccessfully = fileService.loadCurrencyRatesData(currencyRates);

        // If loading exchange rates data from a file fails inform the user and exit the program
        if (!isDataLoadedSuccessfully) {
            System.err.println("ERROR: Loading currency rates data from a file failed. Exiting the program...");
            System.exit(0);
        }
    }

    public void convertPrintToSTDOUT(String amount, String fromCurrency, String toCurrency) {
        // Probably better idea in general to pass amount as BigDecimal instead of string
        BigDecimal bdAmount = new BigDecimal(NumberUtils.toDotDecimalSeparator(amount));

        // Convert currency
        BigDecimal convertedAmount = convertCurrency(bdAmount, fromCurrency, toCurrency, currencyRates, 0);

        // Format the result up to 18 decimal places hiding more than 2 zeros
//        DecimalFormat df = new DecimalFormat("0.00################");
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(SCALE);
        df.setMinimumFractionDigits(2);
        df.setRoundingMode(ROUNDING_MODE);

        // Print the result to STDOUT
        System.out.println(df.format(bdAmount) + " " + fromCurrency + " => " + df.format(convertedAmount) + " " + toCurrency);
    }

    /*
     * To be on the safe side method parameters should accept currency names as strings because it is important
     * that currency rates taken from the same CurrencyRates object for the correct calculations in conversion ratio.
     */
    public static BigDecimal convertCurrency(
            BigDecimal amount,
            String fromCurrencyStr,
            String toCurrencyStr,
            CurrencyRates currencyRates,
            double commissionRate
    ) {
        try {
            // Get currencies from the same currencyRates obj for proper conversion rates calculations
            Currency from = currencyRates.getCurrency(fromCurrencyStr);
            Currency to = currencyRates.getCurrency(toCurrencyStr);

            // Calc conversion rate then calc the converted amount into new currency
            BigDecimal conversionRatio = calcConversionRatio(from, to);
            BigDecimal convertedAmount = calcConvertedAmount(amount, conversionRatio);

            // Usually there is a commission fee on currency exchange, fortunately/sorrowful for this task's imaginary user/banker it is 0
            BigDecimal exchangeFee = calcExchangeFee(convertedAmount, commissionRate);
            BigDecimal total = convertedAmount.subtract(exchangeFee, MATH_CONTEXT);

            // Round up to SCALE (18) decimal places using ROUNDING_MODE (HALF_UP)
            return total.setScale(SCALE, ROUNDING_MODE);

        } catch (NoSuchElementException exc) {
            System.err.println("ERROR: Failed to get currency for conversion, reason: " + exc.getMessage() + " \nExiting the program...");
            System.exit(0);
        }
        // Should not reach this point buf if do then return null as error
        return null;
    }

    // Method can be static as no need for 'this' keyword inside
    public static BigDecimal calcConvertedAmount(BigDecimal amount, BigDecimal conversionRatio) {
        return amount.multiply(conversionRatio, MATH_CONTEXT);
    }

    // Method can be static as no need for 'this' keyword inside
    public static BigDecimal calcConversionRatio(Currency from, Currency to) {
        try {
            BigDecimal fromRate = BigDecimal.valueOf(from.getRate());
            BigDecimal toRate = BigDecimal.valueOf(to.getRate());
            return fromRate.divide(toRate, MATH_CONTEXT);
        } catch (NumberFormatException exc) {
            System.err.println("ERROR: Failed to get rates for given currencies for conversion. Caught an exception: " + exc.getMessage());
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal calcExchangeFee(BigDecimal amount, double commissionRate) {
        /*
          Note that BigDecimal's valueOf method converts double to its String representation before converting to BigDecimal,
          therefor it is safer then using double constructor as some numbers, like 0.1D, does not have an exact representation
          in double type so the result in those cases will differ from expected.
         */
        return amount.multiply(BigDecimal.valueOf(commissionRate), MATH_CONTEXT);
    }
}
