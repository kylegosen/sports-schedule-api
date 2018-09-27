package com.gosenk.sports.schedule.common.comparator;

import com.gosenk.sports.schedule.common.entity.Game;

import java.util.Comparator;

public class GameComparator implements Comparator<Game> {
    @Override
    public int compare(Game a, Game b) {
        return a.getStartTime().compareTo(b.getStartTime());
    }
}
