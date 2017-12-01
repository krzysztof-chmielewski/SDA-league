package com.kchmielewski.sda.league.importing;

import com.google.common.io.Files;
import com.kchmielewski.sda.league.model.Player;
import com.kchmielewski.sda.league.model.Team;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class TeamImport {
    private final Team team;

    public TeamImport(String fileName) throws IOException {
        team = readFile(new File(checkNotNull(fileName, "File name cannot be null")));
    }

    TeamImport(File file) throws IOException {
        team = readFile(checkNotNull(file, "File cannot be null"));
    }

    private Team readFile(File file) throws IOException {
        List<String> lines = Files.readLines(file, Charset.defaultCharset());
        checkState(lines.size() > 0, "Team file must contain at least one line");
        Iterator<String> iterator = lines.iterator();
        Team result = new Team(iterator.next());

        while (iterator.hasNext()) {
            String line = iterator.next();
            String[] playerData = line.split(" ");
            checkState(playerData.length == 2, "Player data must contain 2 elements: " + Arrays.toString(playerData));
            result.addPlayer(new Player(playerData[0], playerData[1], result));
        }

        return result;
    }

    public Team team() {
        return team;
    }
}
