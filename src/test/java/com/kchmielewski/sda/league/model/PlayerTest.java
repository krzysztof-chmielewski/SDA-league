package com.kchmielewski.sda.league.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PlayerTest {
    @Test
    public void forNullNameThrowsException() throws Exception {
        assertThatThrownBy(() -> new Player(null, "", null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void forNullSurnameThrowsException() throws Exception {
        assertThatThrownBy(() -> new Player("", null, null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void forNullTeamThrowsException() throws Exception {
        assertThatThrownBy(() -> new Player("", "", null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void forNonnullParamsDoNotThrowsException() throws Exception {
        new Player("", "", new Team(""));
    }
}