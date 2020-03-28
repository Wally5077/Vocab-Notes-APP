package com.home.englishnote.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;

public class Level {
    private final static int[] LEVEL_TABLE = {
            0, 10, 50, 100, 200, 500, 1000, 2000, 4000, 8000, 10000, 30000, 80000, 150000,
            300000, 480000, 600000, 1000000, Integer.MAX_VALUE
    };
    private final static int MAX_LEVEL = LEVEL_TABLE.length - 1;

    private int minExp;
    private int maxExp;
    private int number;
    private final static HashMap<Integer, Level> levelCache = new HashMap<>();

    private Level(int number) {
        this.number = number;
        if (number >= LEVEL_TABLE.length) {
            throw new IllegalArgumentException("The level number should not exceed " + LEVEL_TABLE.length);
        }
        minExp = LEVEL_TABLE[number - 1];
        maxExp = LEVEL_TABLE[number] - 1;
    }

    public static Level getLevelFromExp(int curExp) {
        curExp = Math.max(0, curExp);
        for (int lv = LEVEL_TABLE.length - 1; lv >= 1; lv--) {
            if (LEVEL_TABLE[lv - 1] <= curExp) {
                return getLevel(lv);
            }
        }
        return getLevel(1);
    }

    public static Level getLevel(int lv) {
        return levelCache.computeIfAbsent(lv, Level::new);
    }

    public static Level getMaxLevel() {
        return getLevel(MAX_LEVEL);
    }
}
