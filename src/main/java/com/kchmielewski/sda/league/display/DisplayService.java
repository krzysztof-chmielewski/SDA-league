package com.kchmielewski.sda.league.display;

import com.google.common.io.Files;
import com.kchmielewski.sda.league.importing.ImportService;
import com.kchmielewski.sda.league.model.Team;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

public class DisplayService {
    private final String prefix = "display";

    public DisplayService(ScheduledExecutorService service, ImportService importService, String displayDirectory,
                          String processedDirectory, String errorDirectory, int delay) {
        checkNotNull(importService);
        checkNotNull(displayDirectory);

        Runnable runnable = () -> {
            File directory = new File(displayDirectory);
            File[] files = directory.listFiles();
            if (files != null) {
                Stream.of(files).filter(f -> f.getName().startsWith(prefix)).forEach(f -> {
                    try {
                        List<String> lines = Files.readLines(f, Charset.defaultCharset());
                        lines.forEach(l -> {
                            try {
                                if (!importService.teams().containsKey(l)) {
                                    System.out.println("Team " + l + " does not exist");
                                    Files.move(f, new File(errorDirectory + "/" + f.getName()));
                                } else {
                                    Team team = importService.teams().get(l);
                                    Files.move(f, new File(processedDirectory + "/" + f.getName()));
                                    System.out.println(team);
                                    System.out.println(team.players());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        };
        service.scheduleWithFixedDelay(runnable, 1, 1, TimeUnit.SECONDS);
    }
}
