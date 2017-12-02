package com.kchmielewski.sda.league.importing;

import com.kchmielewski.sda.league.model.Team;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamServiceTest extends DirectoriesCleanup {
    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    @Test
    public void emptyFileIsMovedToErrorDirectory() throws Exception {
        copyFile("teamEmptyTeamFile");

        TeamService teamService = new TeamService(service, getImportDirectory(), getProcessedDirectory(),
                getErrorDirectory(), 1);
        waitForService(service);

        File[] files = new File(getErrorDirectory()).listFiles();

        assertThat(files).isNotNull().hasSize(1);
        assertThat(teamService.teams()).hasSize(0);
    }

    @Test
    public void nonEmptyFileIsMovedToProcessedDirectoryAndTeamIsCreated() throws Exception {
        copyFile("teamWithoutPlayers");

        TeamService teamService = new TeamService(service, getImportDirectory(), getProcessedDirectory(),
                getErrorDirectory(), 1);
        waitForService(service);

        File[] files = new File(getProcessedDirectory()).listFiles();

        assertThat(files).isNotNull().hasSize(1);
        assertThat(teamService.teams()).hasSize(1).containsValues(new Team("Liverpool FC"));
    }

    @Test
    public void nonEmptyFileIsMovedToProcessedDirectoryAndTeamWithPlayerIsCreated() throws Exception {
        copyFile("teamWithPlayerDataWith2Parts");

        TeamService teamService = new TeamService(service, getImportDirectory(), getProcessedDirectory(),
                getErrorDirectory(), 1);
        waitForService(service);

        File[] files = new File(getProcessedDirectory()).listFiles();

        assertThat(files).isNotNull().hasSize(1);
        assertThat(teamService.teams()).hasSize(1).containsValues(new Team("Liverpool FC"));
        Team team = teamService.teams().get("Liverpool FC");
        assertThat(team.players()).hasSize(1);
    }
}