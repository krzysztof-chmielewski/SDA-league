package com.kchmielewski.sda.league;

import com.kchmielewski.sda.league.display.DisplayService;
import com.kchmielewski.sda.league.importing.ImportService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        ImportService importService = new ImportService(service, "src/main/resources/import",
                "src/main/resources/processed", "src/main/resources/error", 1);
        DisplayService displayService = new DisplayService(service, importService, "src/main/resources/import",
                "src/main/resources/processed", "src/main/resources/error", 1);
    }
}
