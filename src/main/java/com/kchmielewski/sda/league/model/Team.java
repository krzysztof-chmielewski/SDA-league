package com.kchmielewski.sda.league.model;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Team {
    private final String name;
    private final Set<Player> players = new HashSet<>();

    public Team(String name) {
        this.name = checkNotNull(name, "Team name is null");
    }

    public String name() {
        return name;
    }

    public Set<Player> players() {
        return players;
    }

    public void addPlayer(Player player) {
        checkNotNull(player, "Player cannot be null");
        checkArgument(!players.contains(player), "Player " + player + " already plays in " + this);
        players.add(player);
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return name != null ? name.equals(team.name) : team.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
