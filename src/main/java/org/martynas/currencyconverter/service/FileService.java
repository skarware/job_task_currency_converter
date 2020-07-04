package org.martynas.currencyconverter.service;

import com.opencsv.CSVReader;
import org.martynas.currencyconverter.model.Currency;
import org.martynas.currencyconverter.model.CurrencyRates;
import org.martynas.currencyconverter.util.FileUtil;
import org.martynas.currencyconverter.util.NumberUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class FileService {

    private static final String FILE_NAME = "src/main/resources/exchange_rates/data.csv";
    private static final File FILE = new File(FILE_NAME);

    public boolean loadCurrencyRatesData(CurrencyRates currencyRates) {
        System.out.print("Loading exchange rates from a FILE...");
        if (readCSVFile(currencyRates)) {
            // If data loading from a file without errors inform the user and return true
            if (currencyRates.getSize() > 0) {
                System.out.println("\tdata successfully loaded.");
            } else {
                System.out.println("\tdata FILE is empty.");
            }
            return true;
        } else {
            // if data loading from a file failed inform the user and return false
            System.err.println("\tERROR: Failed to load data from a file.");
            return false;
        }
    }

    /**
     * Read CSV string lines from a file, convert to Currency objects and add to currencyRates
     */
    private boolean readCSVFile(CurrencyRates currencyRates) {
        // Read file into BufferedReader
        try (BufferedReader bufferedReader = FileUtil.readFile(FILE)) {
            if (bufferedReader != null) {
                // If not null convert BufferedReader to CSVReader
                try (CSVReader reader = new CSVReader(bufferedReader)) {
                    // define currency obj outside for block to avoid new instantiation on every iteration
                    Currency currencyRate;
                    for (String[] nextLine : reader) {

                        // Create currencyRate obj from read CSV line
                        currencyRate = csvLineToCurrencyBeanMaker(nextLine);

                        // If currencyRate not null add to currencyRates else fatal error happen exit method by with return false
                        if (currencyRate == null) {
                            return false;
                        }

                        // Add new currencyRate object into currencyRates, If failed inform the user but continue the effort to parse CSV file
                        if (!currencyRates.addCurrency(currencyRate)) {
                            System.err.println("ERROR: Failed to add currencyRate object: " + currencyRate + " from a file");
                        }
                    }
                    // if this point reached return true as no fatal errors occur
                    return true;
                }
            } else {
                // Read file operation failed return false
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ERROR: Caught IOException while trying to read and currencyRates data from a file: \n" + e.getMessage());
            return false;
        }
    }

    /**
     * Create currencyRate object from CSV line
     */
    private Currency csvLineToCurrencyBeanMaker(String[] csvLine) {
        if (csvLine == null) return null;
        String name = csvLine[0];
        double rate = 0;
        try {
            rate = Double.parseDouble(NumberUtils.toDotDecimalSeparator(csvLine[1]));
        } catch (NumberFormatException e) {
            System.err.println("ERROR: Caught NumberFormatException while trying to parse bufferedCSVStrings string type into double type.");
            return null;
        }
        // Create and return new Currency obj with name and rate values parsed from CSV file line
        return new Currency(name, rate);
    }

}
