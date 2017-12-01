package com.kchmielewski.sda.league.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TeamTest {
    private final Team team = new Team("team name");

    @Test
    public void forNullTeamNameThrowsException() throws Exception {
        assertThatThrownBy(() -> new Team(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void forNonnullTeamNameDoNotThrowsException() throws Exception {
        new Team("");
    }

    @Test
    public void addPlayerForNullThrowsException() throws Exception {
        assertThatThrownBy(() -> team.addPlayer(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void addingSamePlayerTwiceThrowsException() throws Exception {
        Player player1 = new Player("Name", "Surname", team);
        Player player2 = new Player("Name", "Surname", team);
        team.addPlayer(player1);
        assertThatThrownBy(() -> team.addPlayer(player2)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void addingDifferentPlayersDoNotThrowException() throws Exception {
        Player player1 = new Player("Name1", "Surname1", team);
        Player player2 = new Player("Name2", "Surname2", team);
        team.addPlayer(player1);
        team.addPlayer(player2);
        assertThat(team.players()).contains(player1, player2);
    }
}