package org.martynas.currencyconverter.model;

public class Currency {

    private String name;

    /*
     * Longest exchange rate on given data for the task is 14 digits;
     * double takes only 24 bytes of memory and holds up to 16 decimal digits,
     * while BigDecimal and String for 15 digits would require at least 72 bytes;
     * for given task double is sufficient to hold any exchange rate in data file.
     */
    private double rate;

    public Currency(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }


    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "name='" + name + '\'' +
                ", rate=" + rate +
                '}';
    }
}
