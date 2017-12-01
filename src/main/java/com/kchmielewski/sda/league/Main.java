package com.kchmielewski.sda.league;

import com.kchmielewski.sda.league.importing.ImportService;

public class Main {
    public static void main(String[] args) {
        new ImportService("src/main/resources/import", "src/main/resources/processed", "src/main/resources/error", 1);
    }
}
