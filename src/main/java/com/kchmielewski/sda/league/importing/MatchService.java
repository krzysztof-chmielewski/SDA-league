package com.kchmielewski.sda.league.importing;

import com.google.common.io.Files;
import com.kchmielewski.sda.league.model.Match;
import com.kchmielewski.sda.league.model.Player;
import com.kchmielewski.sda.league.model.Team;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;

public class MatchService {
    private final String prefix = "match";
    private final List<Match> matches = new CopyOnWriteArrayList<>();

    public MatchService(ScheduledExecutorService service, TeamService teamService, String displayDirectory,
                        String processedDirectory, String errorDirectory, int delay) {
        checkNotNull(teamService);
        checkNotNull(displayDirectory);

        Runnable runnable = () -> {
            File directory = new File(displayDirectory);
            File[] files = directory.listFiles();
            if (files != null) {
                Stream.of(files).filter(f -> f.getName().startsWith(prefix)).forEach(f -> {
                    try {
                        List<String> lines = Files.readLines(f, Charset.defaultCharset());
                        if (lines.size() != 2) {
                            System.out.println("File " + f.getName() + " must contain exactly 2 lines, " + lines.size() + " given");
                            Files.move(f, new File(errorDirectory + "/" + f.getName()));
                        } else {
                            String[] firstLine = lines.get(0).split(";");
                            String[] secondLine = lines.get(1).split(";");
                            Team host = new Team(firstLine[0]);
                            Team guest = new Team(secondLine[1]);
                            List<Player> hostShooters = shooters(firstLine, host);
                            List<Player> guestShooters = shooters(secondLine, guest);

                            matches.add(new Match(host, guest, hostShooters, guestShooters));
                            Files.move(f, new File(processedDirectory + "/" + f.getName()));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        };
        service.scheduleWithFixedDelay(runnable, delay, delay, TimeUnit.MILLISECONDS);
    }

    public List<Match> matches() {
        return matches;
    }

    private List<Player> shooters(String[] line, Team team) {
        List<Player> players = new ArrayList<>(line.length - 1);
        for (int i = 1; i < line.length; i++) {
            players.add(new Player(line[1], line[2], team));
        }

        return players;
    }
}
