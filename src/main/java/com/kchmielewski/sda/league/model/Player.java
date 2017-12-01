package com.kchmielewski.sda.league.model;

import static com.google.common.base.Preconditions.checkNotNull;

public class Player {
    private final String name;
    private final String surname;
    private final Team team;

    public Player(String name, String surname, Team team) {
        this.name = checkNotNull(name, "Player name is null");
        this.surname = checkNotNull(surname, "Player surname is null");
        this.team = checkNotNull(team, "Team is null");
    }

    public String name() {
        return name;
    }

    public String surname() {
        return surname;
    }

    public Team team() {
        return team;
    }

    @Override
    public String toString() {
        return "Player " + name + " " + surname + " plays in the " + team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (name != null ? !name.equals(player.name) : player.name != null) return false;
        return surname != null ? surname.equals(player.surname) : player.surname == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }
}
