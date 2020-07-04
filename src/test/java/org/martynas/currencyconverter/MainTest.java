package org.martynas.currencyconverter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void given2USDEUR_whenMain_thenDoesNotThrow() {
        String[] args = new String[]{"2", "USD", "EUR"};
        Executable executable = () -> org.martynas.currencyconverter.Main.main(args);
        assertDoesNotThrow(executable);
    }

    @Test
    void givenNotEnoughArgs_whenMain_thenDoesNotThrow() {
        String[] args = new String[]{"2", "EUR"};
        Executable executable = () -> org.martynas.currencyconverter.Main.main(args);
        assertDoesNotThrow(executable);
    }
}