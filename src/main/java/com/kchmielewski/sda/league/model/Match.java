package com.kchmielewski.sda.league.model;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class Match {
    private final Team host;
    private final Team guest;
    private final List<Player> hostShooters;
    private final List<Player> guestShooters;
    private final int hostScore;
    private final int guestScore;


    public Match(Team host, Team guest, List<Player> hostShooters, List<Player> guestShooters) {
        this.host = checkNotNull(host);
        this.guest = checkNotNull(guest);
        this.hostShooters = checkNotNull(hostShooters);
        this.guestShooters = checkNotNull(guestShooters);
        hostScore = hostShooters.size();
        guestScore = guestShooters.size();
    }

    public Team host() {
        return host;
    }

    public Team guest() {
        return guest;
    }

    public List<Player> hostShooters() {
        return hostShooters;
    }

    public List<Player> guestShooters() {
        return guestShooters;
    }

    public int hostScore() {
        return hostScore;
    }

    public int guestScore() {
        return guestScore;
    }
}
