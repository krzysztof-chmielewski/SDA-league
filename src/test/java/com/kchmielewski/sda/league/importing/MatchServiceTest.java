package com.kchmielewski.sda.league.importing;

import org.junit.Test;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MatchServiceTest extends DirectoriesCleanup {
    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    private final TeamService teamService = mock(TeamService.class);

    @Test
    public void fileWithZeroLinesIsMovedToErrorDirectory() throws Exception {
        copyFile("matchEmpty");

        new MatchService(service, teamService, getImportDirectory(), getProcessedDirectory(), getErrorDirectory(), 1);
        waitForService(service);

        File[] files = new File(getErrorDirectory()).listFiles();

        assertThat(files).isNotNull().hasSize(1);
    }

    @Test
    public void fileWithOneLinesIsMovedToErrorDirectory() throws Exception {
        copyFile("matchOneTeam");

        new MatchService(service, teamService, getImportDirectory(), getProcessedDirectory(), getErrorDirectory(), 1);
        waitForService(service);

        File[] files = new File(getErrorDirectory()).listFiles();

        assertThat(files).isNotNull().hasSize(1);
    }

    @Test
    public void fileWithThreeLinesIsMovedToErrorDirectory() throws Exception {
        copyFile("matchThreeTeams");

        new MatchService(service, teamService, getImportDirectory(), getProcessedDirectory(), getErrorDirectory(), 1);
        waitForService(service);

        File[] files = new File(getErrorDirectory()).listFiles();

        assertThat(files).isNotNull().hasSize(1);
    }
}