package com.kchmielewski.sda.league;

import com.kchmielewski.sda.league.importing.DisplayService;
import com.kchmielewski.sda.league.importing.TeamService;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        TeamService teamService = new TeamService(service, "src/main/resources/import",
                "src/main/resources/processed", "src/main/resources/error", 1);
        DisplayService displayService = new DisplayService(service, teamService, "src/main/resources/import",
                "src/main/resources/processed", "src/main/resources/error", 1);
    }
}
