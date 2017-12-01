package com.kchmielewski.sda.league.importing;

import com.google.common.io.Files;
import com.kchmielewski.sda.league.model.Team;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

public class ImportService {
    private final Map<String, Team> teams = new ConcurrentHashMap<>();

    public ImportService(ScheduledExecutorService service, String importDirectory, String processedDirectory, String errorDirectory, int delay) {
        checkNotNull(importDirectory);
        checkNotNull(processedDirectory);
        checkNotNull(errorDirectory);

        Runnable runnable = () -> {
            File directory = new File(importDirectory);
            File[] files = directory.listFiles();
            if (files != null) {
                Stream.of(files).forEach(f -> {
                    try {
                        TeamImport teamImport = new TeamImport(f);
                        teams.putIfAbsent(teamImport.team().name(), teamImport.team());
                        Files.move(f, new File(processedDirectory + "/" + f.getName()));
                        System.out.println("Loaded team " + teamImport.team());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        try {
                            Files.move(f, new File(errorDirectory + "/" + f.getName()));
                            System.out.println("Could not load team " + f.getName());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        };
        service.scheduleWithFixedDelay(runnable, delay, delay, TimeUnit.SECONDS);
    }


    public Map<String, Team> teams() {
        return teams;
    }
}
