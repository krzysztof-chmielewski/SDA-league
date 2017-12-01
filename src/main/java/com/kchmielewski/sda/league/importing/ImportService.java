package com.kchmielewski.sda.league.importing;

import com.google.common.io.Files;
import com.kchmielewski.sda.league.model.Team;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

public class ImportService {
    private final Map<String, Team> teams = new ConcurrentHashMap<>();

    public ImportService(String importDirectory, String processedDirectory, String errorDirectory, int delay) {
        checkNotNull(importDirectory);
        checkNotNull(processedDirectory);
        checkNotNull(errorDirectory);

        Runnable runnable = () -> {
            File directory = new File(importDirectory);
            File[] files = directory.listFiles();
            if (files != null) {
                Stream.of(files).forEach(f -> {
                    try {
                        teams.putIfAbsent(f.getName(), new TeamImport(f).team());
                        Files.move(f, new File(processedDirectory + "/" + f.getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        try {
                            Files.move(f, new File(errorDirectory + "/" + f.getName()));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
            }
        };
        ScheduledExecutorService service = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        service.scheduleWithFixedDelay(runnable, delay, delay, TimeUnit.SECONDS);
    }


    public Map<String, Team> teams() {
        return teams;
    }
}
