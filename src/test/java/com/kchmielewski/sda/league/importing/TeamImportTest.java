package com.kchmielewski.sda.league.importing;

import com.kchmielewski.sda.league.model.Player;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TeamImportTest {
    @Test
    public void forNullFileNameThrowsException() throws Exception {
        assertThatThrownBy(() -> new TeamImport((String) null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void forNullFileThrowsException() throws Exception {
        assertThatThrownBy(() -> new TeamImport((File) null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void forEmptyFileThrowsException() throws Exception {
        assertThatThrownBy(() -> new TeamImport("src/test/resources/import/teamEmptyTeamFile.txt")).isInstanceOf
                (IllegalStateException.class);
    }

    @Test
    public void forOneLineFileReturnsTeamWithThatLineAsAName() throws Exception {
        TeamImport teamImport = new TeamImport("src/test/resources/import/teamWithoutPlayers.txt");
        assertThat(teamImport.team().name()).isEqualTo("Liverpool FC");
    }

    @Test
    public void forTeamWithPlayerDataContainingLessThan2PartsThrowsException() throws Exception {
        assertThatThrownBy(() -> new TeamImport("src/test/resources/import/teamWithPlayerDataWithLessThan2Parts.txt")
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void forTeamWithPlayerDataContainingMoreThan2PartsThrowsException() throws Exception {
        assertThatThrownBy(() -> new TeamImport("src/test/resources/import/teamWithPlayerDataWithMoreThan2Parts.txt")
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void forTeamWithPlayerDataContaining2PartsTeamContainsThatPlayer() throws Exception {
        TeamImport teamImport = new TeamImport("src/test/resources/import/teamWithPlayerDataWith2Parts.txt");
        assertThat(teamImport.team().players()).hasSize(1).contains(new Player("Adam", "Lallana", teamImport.team()));
    }
}