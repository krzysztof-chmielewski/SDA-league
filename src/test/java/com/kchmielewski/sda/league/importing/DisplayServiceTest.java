package com.kchmielewski.sda.league.importing;

import com.kchmielewski.sda.league.model.Team;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DisplayServiceTest extends DirectoriesCleanup {
    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    private final TeamService teamService = mock(TeamService.class);

    @Test
    public void emptyFileIsMovedToErrorDirectory() throws Exception {
        copyFile("displayEmptyTeamFile");

        new DisplayService(service, teamService, getImportDirectory(), getProcessedDirectory(), getErrorDirectory(), 1);
        waitForService(service);

        File[] files = new File(getErrorDirectory()).listFiles();

        assertThat(files).isNotNull().hasSize(1);
    }

    @Test
    public void nonEmptyFileIfTeamDoesNotExistIsMovedToErrorDirectory() throws Exception {
        copyFile("displayLiverpool");

        new DisplayService(service, teamService, getImportDirectory(), getProcessedDirectory(), getErrorDirectory(), 1);
        waitForService(service);

        File[] files = new File(getErrorDirectory()).listFiles();

        assertThat(files).isNotNull().hasSize(1);
    }

    @Test
    public void nonEmptyFileIfTeamExistsIsMovedToProcessedDirectory() throws Exception {
        copyFile("displayLiverpool");
        given(teamService.teams()).willReturn(Collections.singletonMap("Liverpool FC", mock(Team.class)));

        new DisplayService(service, teamService, getImportDirectory(), getProcessedDirectory(), getErrorDirectory(), 1);
        waitForService(service);

        File[] files = new File(getProcessedDirectory()).listFiles();

        assertThat(files).isNotNull().hasSize(1);
    }
}